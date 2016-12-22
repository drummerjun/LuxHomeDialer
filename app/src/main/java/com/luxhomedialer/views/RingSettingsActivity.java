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
import android.widget.RadioGroup;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;
import com.jun.luxhomedialer.database.SystemDataSource;

import java.io.IOException;

public class RingSettingsActivity extends AppCompatActivity {
    private static final String TAG = RingSettingsActivity.class.getSimpleName();
    private static final int MIN_DELAY = 1;
    private static final int MAX_DELAY = 9;
    private static final int DEFAULT_TIME = 3;
    private static final String[] VOL_SETTINGS = {"2", "1", "0"};
    private NumberPicker mPicker;
    private RadioGroup mGroup;

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
        setContentView(R.layout.activity_ring);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        mPicker = (NumberPicker)findViewById(R.id.np_ringtime);
        mPicker.setMinValue(MIN_DELAY);
        mPicker.setMaxValue(MAX_DELAY);

        Button okButton = (Button)findViewById(R.id.btn_confirm);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {getResources().getString(R.string.dialog_siren_vol),
                        getResources().getString(R.string.dialog_siren_time)};

                AlertDialog.Builder builder = new AlertDialog.Builder(RingSettingsActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        sendSMS(item);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
        int radioId = pref.getInt(Constants.VOL, 0);
        if(radioId == 0) {
            radioId = R.id.radio0;
        }
        int savedTime = pref.getInt(Constants.RINGTIME, 0);
        if(savedTime == 0) {
            savedTime = DEFAULT_TIME;
        }
        mGroup.check(radioId);
        mPicker.setValue(savedTime);
    }

    private void sendSMS(final int itemIndex) {
        new AlertDialog.Builder(new ContextThemeWrapper(RingSettingsActivity.this,
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
                        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
                        final String number = pref.getString(Constants.NUMBER, "");
                        final int ringTime = mPicker.getValue();
                        SmsManager sms = SmsManager.getDefault();
                        if(itemIndex == 1) {
                            String msgBody = "";
                            sms.sendTextMessage(number, null, msgBody, null, null);
                            saveData(pref, ringTime, -1);
                        } else if(itemIndex == 0) {
                            int selectedVolumeId = mGroup.getCheckedRadioButtonId();
                            String ringVol = getRingVolume(selectedVolumeId);
                            if (!ringVol.isEmpty()) {
                                String ringMsgBody = "";
                                sms.sendTextMessage(number, null, ringMsgBody, null, null);
                            }
                            saveData(pref, -1, selectedVolumeId);
                        }
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void saveData(SharedPreferences pref, final int duration, final int volumeId) {
        final int _id = pref.getInt(Constants.KEY_ID, -1);
        SharedPreferences.Editor editor = pref.edit();
        if(duration > -1) {
            editor.putInt(Constants.RINGTIME, duration).apply();
        }

        if(volumeId > -1) {
            editor.putInt(Constants.VOL, volumeId).apply();
        }

        if(_id > -1) {
            final SystemDataSource dataSource = new SystemDataSource(RingSettingsActivity.this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int returnCode = -1;
                    try {
                        dataSource.open();
                        ContentValues contentValues = new ContentValues();
                        if(duration > -1) {
                            contentValues.put(Constants.RINGTIME, duration);
                        }
                        if(volumeId > -1) {
                            contentValues.put(Constants.VOL, volumeId);
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

    private String getRingVolume(int selectedId) {
        String ret = "";
        View selectedButton = mGroup.findViewById(selectedId);
        int selectedIndex = mGroup.indexOfChild(selectedButton);
        switch(selectedIndex) {
            case 0:
                ret = "2";
                break;
            case 1:
                ret = "1";
                break;
            case 2:
                ret = "0";
                break;
            default:
        }
        return ret;
    }
}
