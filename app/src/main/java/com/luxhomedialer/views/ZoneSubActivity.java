package com.jun.luxhomedialer.views;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.LoadBitmapTask;
import com.jun.luxhomedialer.R;
import com.jun.luxhomedialer.database.SystemDataSource;

import java.io.IOException;

public class ZoneSubActivity extends AppCompatActivity{
    private static final String TAG = ZoneSubActivity.class.getSimpleName();
    private static final int CODE_CAMERA = 201;
    private static final int CODE_GALLERY = 202;
    private int ZONE_ID = -1;
    private ImageView zoneImage;
    private EditText zoneEditor;
    private String uriString = "";
    private String zoneString;

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
        setContentView(R.layout.activity_zonesub);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE);
        ZONE_ID = getIntent().getIntExtra(Constants.ZONE, ZONE_ID);
        Resources res = getResources();
        String hint = String.format(res.getString(R.string.default_zone), ZONE_ID);
        if(ZONE_ID == -1) {
            finish();
        } else {
            getSupportActionBar().setTitle(hint);
        }

        zoneString = pref.getString(Constants.ZONE + String.valueOf(ZONE_ID), "");
        zoneEditor = (EditText)findViewById(R.id.edittext_zone);
        zoneEditor.setText(zoneString);
        uriString = pref.getString(Constants.URIZONE + String.valueOf(ZONE_ID), "");
        zoneImage = (ImageView)findViewById(R.id.img_thumbnail);
        if(!uriString.isEmpty()) {
            new LoadBitmapTask().execute(this.getContentResolver(), uriString, zoneImage);
        }
        zoneImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {getResources().getString(R.string.dialog_camera),
                        getResources().getString(R.string.dialog_gallery)};

                AlertDialog.Builder builder = new AlertDialog.Builder(ZoneSubActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, CODE_CAMERA);
                                break;
                            case 1:
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, CODE_GALLERY);
                                break;
                            default:
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Button okButton = (Button)findViewById(R.id.btn_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoneString = zoneEditor.getText().toString();
                if(zoneString.isEmpty() && uriString.isEmpty()) {
                    finish();
                } else if(zoneString.isEmpty()) {
                    new AlertDialog.Builder(new ContextThemeWrapper(ZoneSubActivity.this, R.style.AlertDialogTheme))
                            .setMessage(R.string.empty_num)
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    new AlertDialog.Builder(new ContextThemeWrapper(ZoneSubActivity.this, R.style.AlertDialogTheme))
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
                                    SharedPreferences.Editor editor = pref.edit();
                                    final int _id = pref.getInt(Constants.KEY_ID, -1);
                                    editor.putString(Constants.ZONE + ZONE_ID, zoneString).apply();
                                    editor.putString(Constants.URIZONE + ZONE_ID, uriString).apply();

                                    if (_id > -1) {
                                        final SystemDataSource dataSource = new SystemDataSource(ZoneSubActivity.this);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                int returnCode = 0;
                                                try {
                                                    dataSource.open();
                                                    ContentValues contentValues = new ContentValues();
                                                    contentValues.put(Constants.ZONE + String.valueOf(ZONE_ID), zoneString);
                                                    contentValues.put(Constants.URIZONE + String.valueOf(ZONE_ID), uriString);
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

                                    final String number = pref.getString(Constants.NUMBER, "");
                                    SmsManager sms = SmsManager.getDefault();
                                    sms.sendTextMessage(number, null, "", null, null);

                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case CODE_CAMERA:
            case CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    Uri uriImage = data.getData();
                    new LoadBitmapTask().execute(this.getContentResolver(), uriImage, zoneImage);
                    uriString = uriImage.toString();
                }
                break;
            default:
        }
    }
}
