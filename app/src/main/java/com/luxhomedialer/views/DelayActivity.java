package com.jun.luxhomedialer.views;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;
import com.jun.luxhomedialer.database.SystemDataSource;

import java.io.IOException;

public class DelayActivity extends AppCompatActivity {
    private static final String TAG = DelayActivity.class.getSimpleName();
    private static final int MIN_DELAY = 0;
    private static final int MAX_DELAY = 240;
    private static final int DEFAULT = 15;
    private NumberPicker mPicker;

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
        setContentView(R.layout.activity_delay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPicker = (NumberPicker)findViewById(R.id.np_ringdelay);
        mPicker.setMinValue(MIN_DELAY);
        mPicker.setMaxValue(MAX_DELAY);

        Button okButton = (Button)findViewById(R.id.btn_confirm);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(DelayActivity.this, R.style.AlertDialogTheme))
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
                                SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
                                final String number = pref.getString(Constants.NUMBER, "");
                                final int value = mPicker.getValue();
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(number, null, "", null, null);

                                saveData(pref, value);
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
        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
        int savedValue = pref.getInt(Constants.DELAY, DEFAULT);
        if(savedValue == 0) {
            savedValue = DEFAULT;
        }
        mPicker.setValue(savedValue);
    }

    private void saveData(SharedPreferences pref, final int value) {
        final int _id = pref.getInt(Constants.KEY_ID, -1);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Constants.DELAY, value).apply();

        if(_id > -1) {
            final SystemDataSource dataSource = new SystemDataSource(DelayActivity.this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int returnCode = -1;
                    try {
                        dataSource.open();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Constants.DELAY, value);
                        returnCode = dataSource.updateExistingSystemRow(_id, contentValues);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } finally {
                        Log.d(TAG, "update result=" + returnCode);
                        dataSource.close();
                    }
                }
            }).start();
        }
    }
}
