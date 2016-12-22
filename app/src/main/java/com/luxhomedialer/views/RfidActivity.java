package com.jun.luxhomedialer.views;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.LoadBitmapTask;
import com.jun.luxhomedialer.R;
import com.jun.luxhomedialer.database.SystemDataSource;

import java.io.IOException;

public class RfidActivity extends AppCompatActivity {
    private static final String TAG = RfidActivity.class.getSimpleName();
    private static final String KEY_TEMP = "TEMPKEY";
    private static final String KEY_IMG = "TEMPIMG";
    private TextView tagText1, tagText2, tagText3, tagText4;
    private ImageView uriImage1, uriImage2, uriImage3, uriImage4;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RelativeLayout layout_tag1 = (RelativeLayout)findViewById(R.id.group_rfid1);
        layout_tag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(1);
            }
        });

        RelativeLayout layout_tag2 = (RelativeLayout)findViewById(R.id.group_rfid2);
        layout_tag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(2);
            }
        });

        RelativeLayout layout_tag3 = (RelativeLayout)findViewById(R.id.group_rfid3);
        layout_tag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(3);
            }
        });

        RelativeLayout layout_tag4 = (RelativeLayout)findViewById(R.id.group_rfid4);
        layout_tag4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(4);
            }
        });

        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE);
        String tags[] = new String[Constants.RFIDTAGSUM];
        String uriStrings[] = new String[Constants.RFIDTAGSUM];

        for(int i = 0; i < Constants.RFIDTAGSUM; i++) {
            uriStrings[i] = pref.getString(Constants.URIRFID + String.valueOf(i+1), "");
            tags[i] = pref.getString(Constants.RFIDTAG + String.valueOf(i+1), "");
            Log.d(TAG, "tag=" + tags[i]);
                    //getResources().getString(R.string.default_empty));
        }

        tagText1 = (TextView)findViewById(R.id.text_rfid1);
        tagText2 = (TextView)findViewById(R.id.text_rfid2);
        tagText3 = (TextView)findViewById(R.id.text_rfid3);
        tagText4 = (TextView)findViewById(R.id.text_rfid4);
        String label = "";
        if(tags[0].isEmpty()) {
            label = String.format(getResources().getString(R.string.default_tag), 1);
            Log.d(TAG, "tag1=" + label);
            tagText1.setText(label);
        } else {
            tagText1.setText(tags[0]);
        }

        if(tags[1].isEmpty()) {
            label = String.format(getResources().getString(R.string.default_tag), 2);
            Log.d(TAG, "tag2=" + label);
            tagText2.setText(label);
        } else {
            tagText2.setText(tags[1]);
        }

        if(tags[2].isEmpty()) {
            label = String.format(getResources().getString(R.string.default_tag), 3);
            Log.d(TAG, "tag3=" + label);
            tagText3.setText(label);
        } else {
            tagText3.setText(tags[2]);
        }

        if(tags[3].isEmpty()) {
            label = String.format(getResources().getString(R.string.default_tag), 4);
            Log.d(TAG, "tag4=" + label);
            tagText4.setText(label);
        } else {
            tagText4.setText(tags[3]);
        }

        uriImage1 = (ImageView)findViewById(R.id.img_rfid1);
        uriImage2 = (ImageView)findViewById(R.id.img_rfid2);
        uriImage3 = (ImageView)findViewById(R.id.img_rfid3);
        uriImage4 = (ImageView)findViewById(R.id.img_rfid4);

        if(!uriStrings[0].isEmpty()) {
            new LoadBitmapTask().execute(this.getContentResolver(), uriStrings[0], uriImage1);
        }
        if(!uriStrings[1].isEmpty()) {
            new LoadBitmapTask().execute(this.getContentResolver(), uriStrings[1], uriImage2);
        }
        if(!uriStrings[2].isEmpty()) {
            new LoadBitmapTask().execute(this.getContentResolver(), uriStrings[2], uriImage3);
        }
        if(!uriStrings[3].isEmpty()) {
            new LoadBitmapTask().execute(this.getContentResolver(), uriStrings[3], uriImage4);
        }

        Button okButton = (Button)findViewById(R.id.btn_confirm);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(RfidActivity.this, R.style.AlertDialogTheme))
                        .setMessage(R.string.msg_confirm_sms)
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
                                final String number = pref.getString(Constants.NUMBER, "");
                                final int _id = pref.getInt(Constants.KEY_ID, -1);
                                String msg_body = "";
                                String[] tags = getTags();
                                saveTags(_id, tags, pref);

                                Log.d(TAG, "number=" + number + "; msgBody=" + msg_body);
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(number, null, msg_body, null, null);
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE);
        String[] tempTags = new String[Constants.RFIDTAGSUM];
        String[] tempUri = new String[Constants.RFIDTAGSUM];
        for(int i = 0; i < Constants.RFIDTAGSUM; i++) {
            tempTags[i] = pref.getString(KEY_TEMP + String.valueOf(i+1), "");
            tempUri[i] = pref.getString(KEY_IMG + String.valueOf(i+1), "");
            Log.d(TAG, "temp tag" + (i+1) + "=" + tempTags[i]);
            Log.d(TAG, "temp uri" + (i+1) + "=" + tempUri[i]);
        }

        String label = "";
        if(tempTags[0].equals(Constants.RESET)) {
            label = String.format(getResources().getString(R.string.default_tag), 1);
            tagText1.setText(label);
            uriImage1.setImageResource(R.drawable.icon_rfiditem);
        } else if(!tempTags[0].isEmpty()) {
            tagText1.setText(tempTags[0]);
            if(!tempUri[0].isEmpty()) {
                new LoadBitmapTask().execute(this.getContentResolver(), tempUri[0], uriImage1);
            }
        }

        if(tempTags[1].equals(Constants.RESET)) {
            label = String.format(getResources().getString(R.string.default_tag), 2);
            tagText2.setText(label);
            uriImage2.setImageResource(R.drawable.icon_rfiditem);
        } else if(!tempTags[1].isEmpty()) {
            Log.d(TAG, "temp tag2=" + tempTags[1]);
            tagText2.setText(tempTags[1]);
            if(!tempUri[1].isEmpty()) {
                new LoadBitmapTask().execute(this.getContentResolver(), tempUri[1], uriImage2);
            }
        }

        if(tempTags[2].equals(Constants.RESET)) {
            label = String.format(getResources().getString(R.string.default_tag), 3);
            tagText3.setText(label);
            uriImage3.setImageResource(R.drawable.icon_rfiditem);
        } else if(!tempTags[2].isEmpty()) {
            Log.d(TAG, "temp tag3=" + tempTags[2]);
            tagText3.setText(tempTags[2]);
            if(!tempUri[2].isEmpty()) {
                new LoadBitmapTask().execute(this.getContentResolver(), tempUri[2], uriImage3);
            }
        }

        if(tempTags[3].equals(Constants.RESET)) {
            label = String.format(getResources().getString(R.string.default_tag), 4);
            tagText4.setText(label);
            uriImage4.setImageResource(R.drawable.icon_rfiditem);
        } else if(!tempTags[3].isEmpty()) {
            Log.d(TAG, "temp tag4=" + tempTags[3]);
            tagText4.setText(tempTags[3]);
            if(!tempUri[3].isEmpty()) {
                new LoadBitmapTask().execute(this.getContentResolver(), tempUri[3], uriImage4);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE).edit();
        for(int i = 0; i < Constants.RFIDTAGSUM; i++) {
            editor.remove(KEY_TEMP + String.valueOf(i+1));
            editor.remove(KEY_IMG + String.valueOf(i+1));
        }
        editor.apply();
    }

    private void gotoActivity(int tagId) {
        Intent intent = new Intent(RfidActivity.this, RfidSubActivity.class);
        intent.putExtra(Constants.RFIDTAG, tagId);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private String[] getTags() {
        Resources res = getResources();
        String[] tags = new String[Constants.RFIDTAGSUM];
        int i = 0;
        tags[i++] = tagText1.getText().toString();
        tags[i++] = tagText2.getText().toString();
        tags[i++] = tagText3.getText().toString();
        tags[i] = tagText4.getText().toString();

        for(int j = 0; j < Constants.RFIDTAGSUM; j++) {
            String EMPTY = String.format(res.getString(R.string.default_tag), (j + 1));
            if(tags[j].equals(EMPTY)) {
                tags[j] = "";
            }
        }
        return tags;
    }

    private void saveTags(final int _id,
                          final String[] tags, SharedPreferences pref) {
        SharedPreferences.Editor editor = pref.edit();
        final String[] uriImage = new String[Constants.RFIDTAGSUM];
        for(int i = 0; i < Constants.RFIDTAGSUM; i++) {
            Log.d(TAG, "tag=" + tags[i]);
            uriImage[i] = pref.getString(KEY_IMG + String.valueOf(i+1), "");
            editor.putString(Constants.RFIDTAG + String.valueOf(i + 1), tags[i]);
            editor.putString(Constants.URIRFID + String.valueOf(i+1), uriImage[i]);
            editor.remove(KEY_TEMP + String.valueOf(i+1));
            editor.remove(KEY_IMG + String.valueOf(i+1));
        }
        editor.apply();

        if(_id > -1) {
            final SystemDataSource dataSource = new SystemDataSource(RfidActivity.this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int returnCode = 0;
                    try {
                        dataSource.open();
                        ContentValues contentValues = new ContentValues();
                        for (int i = 0; i < Constants.RFIDTAGSUM; i++) {
                            contentValues.put(Constants.RFIDTAG + String.valueOf(i+1), tags[i]);
                            contentValues.put(Constants.URIRFID + String.valueOf(i+1), uriImage[i]);
                        }
                        returnCode = dataSource.updateExistingSystemRow(_id, contentValues);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } finally {
                        dataSource.close();
                    }
                }
            }).start();
        }
    }
}
