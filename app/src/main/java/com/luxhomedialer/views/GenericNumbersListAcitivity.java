package com.jun.luxhomedialer.views;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;
import com.jun.luxhomedialer.adapters.NumberItem;
import com.jun.luxhomedialer.adapters.NumberItemAdapter;
import com.jun.luxhomedialer.database.SystemDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class GenericNumbersListAcitivity extends AppCompatActivity {
    private static final String TAG = GenericNumbersListAcitivity.class.getSimpleName();
    private static final String KEY_TEMP = "TEMPKEY";
    private static final String KEY_URI = "TEMPURI";
    private static final String NUMBER_ID = "NUMBERID";
    private static final int DEFAULT_ID = -1;
    private String function = Constants.CALL;
    private String uriFunction = Constants.URICALL;
    private ArrayList<NumberItem> itemList;
    private NumberItemAdapter mAdapter;
    private ListView mList;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
        Intent intent = getIntent();
        function = intent.getStringExtra(Constants.FUNCTION);
        if (function.equals(Constants.CALL)) {
            uriFunction = Constants.URICALL;
        } else if (function.equals(Constants.SMS)) {
            uriFunction = Constants.URISMS;
        } else if (function.equals(Constants.SPEEDDIAL)) {
            uriFunction = Constants.URISPEED;
        }

        if(itemList == null) {
            itemList = new ArrayList<NumberItem>();
        }

        String uriStrings[] = new String[Constants.CALLSMSSUM];
        String numbers[] = new String[Constants.CALLSMSSUM];
        for(int i = 0; i < Constants.CALLSMSSUM; i++) {
            uriStrings[i] = pref.getString(uriFunction + String.valueOf(i+1), "");
            numbers[i] = pref.getString(function + "" + String.valueOf(i+1), "");
                    //getResources().getString(R.string.default_empty));
            NumberItem item = new NumberItem(uriStrings[i], numbers[i]);
            itemList.add(item);
        }

        mList = (ListView)findViewById(R.id.number_list);
        mAdapter = new NumberItemAdapter(this, itemList);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo: enter number editing activity
                Intent in = new Intent(GenericNumbersListAcitivity.this, NumberEditorActivity.class);
                in.putExtra(Constants.FUNCTION, function);
                in.putExtra(NUMBER_ID, position+1);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Button confirmButton = (Button)findViewById(R.id.btn_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(GenericNumbersListAcitivity.this,
                        R.style.AlertDialogTheme))
                        .setIcon(R.drawable.icon_warn)
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
                                String msgBody = "";
                                SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
                                savePhoneNumbers(pref);
                                final String number = pref.getString(Constants.NUMBER, "");
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(number, null, msgBody, null, null);
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
        setTitle();
        init();
        if(mAdapter == null) {
            mAdapter = new NumberItemAdapter(getApplicationContext(), itemList);
        }
        mList.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        for(int i = 0; i < Constants.CALLSMSSUM; i++) {
            editor.remove(KEY_TEMP + String.valueOf(i + 1));
            editor.remove(KEY_URI + String.valueOf(i+1));
        }
        editor.apply();
    }

    private void setTitle() {
        String title = "";
        if (function.equals(Constants.CALL)) {
            title = getResources().getString(R.string.title_call);
        } else if (function.equals(Constants.SMS)) {
            title = getResources().getString(R.string.title_sms);
        } else if (function.equals(Constants.SPEEDDIAL)) {
            title = getResources().getString(R.string.title_speeddial);
        }
        getSupportActionBar().setTitle(title);
    }

    private void init() {
        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
        for(int i = 0; i < Constants.CALLSMSSUM; i++) {
            String temp = pref.getString(KEY_TEMP + "" + String.valueOf(i+1), "");
            String uriString = pref.getString(KEY_URI + String.valueOf(i+1), "");
            if (temp.isEmpty()) {
                continue;
            } else if(temp.equals(Constants.RESET)) {
                temp = "";
                uriString = "";
            }

            NumberItem item = new NumberItem(uriString, temp);
            itemList.set(i, item);
        }
    }

    private void savePhoneNumbers(SharedPreferences pref) {
        SharedPreferences.Editor editor = pref.edit();
        final int _id = pref.getInt(Constants.KEY_ID, DEFAULT_ID);
        for(int i = 0; i < Constants.CALLSMSSUM; i++) {
            Log.d(TAG, "key=" + function + String.valueOf(i + 1));
            editor.putString(function + String.valueOf(i+1), itemList.get(i).getNumber());
            editor.putString(uriFunction + String.valueOf(i+1), itemList.get(i).getImageUri());
            editor.remove(KEY_TEMP + String.valueOf(i + 1));
            editor.remove(KEY_URI + String.valueOf(i+1));
        }
        editor.apply();

        if(_id > DEFAULT_ID) {
            final SystemDataSource dataSource = new SystemDataSource(GenericNumbersListAcitivity.this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int returnCode = 0;
                    try {
                        dataSource.open();
                        ContentValues contentValues = new ContentValues();
                        for (int i = 0; i < Constants.CALLSMSSUM; i++) {
                            contentValues.put(function + String.valueOf(i+1), itemList.get(i).getNumber());
                            contentValues.put(uriFunction + String.valueOf(i+1), itemList.get(i).getImageUri());
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
