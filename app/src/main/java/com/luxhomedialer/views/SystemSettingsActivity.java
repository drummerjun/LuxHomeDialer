package com.jun.luxhomedialer.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;

public class SystemSettingsActivity extends AppCompatActivity{
    private static final String TAG = SystemSettingsActivity.class.getSimpleName();

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
        setContentView(R.layout.activity_systemsettings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        Button alarmSettingButton = (Button)findViewById(R.id.btn1_alarm);
        alarmSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemSettingsActivity.this, AlarmActivity.class);
                gotoActivity(intent);
            }
        });

        Button inquiryButton = (Button)findViewById(R.id.btn2_current);
        inquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(SystemSettingsActivity.this, R.style.AlertDialogTheme))
                        .setIcon(R.drawable.icon_warn)
                        .setMessage(R.string.msg_inquiry)
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
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(number, null, "", null, null);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        */

        Button ringButon = (Button)findViewById(R.id.btn3_siren);
        ringButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemSettingsActivity.this, RingSettingsActivity.class);
                gotoActivity(intent);
            }
        });

        /*
        Button langButton = (Button)findViewById(R.id.btn4_lang);
        langButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemSettingsActivity.this, LangSettingActivity.class);
                gotoActivity(intent);
            }
        });
        */

        Button notifButton = (Button)findViewById(R.id.btn5_notification);
        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemSettingsActivity.this, NotificationSettingsActivity.class);
                gotoActivity(intent);
            }
        });

        /*
        Button twowayButton = (Button)findViewById(R.id.btn6_twoway);
        twowayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(SystemSettingsActivity.this, R.style.AlertDialogTheme))
                        .setIcon(R.drawable.icon_warn)
                        .setMessage(R.string.msg_callback)
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
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage(number, null, "", null, null);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        */

        Button rfidButton = (Button)findViewById(R.id.btn7_rfid);
        rfidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemSettingsActivity.this, RfidActivity.class);
                gotoActivity(intent);
            }
        });

        Button speedButton = (Button)findViewById(R.id.btn8_speeddial);
        speedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemSettingsActivity.this, GenericNumbersListAcitivity.class);
                intent.putExtra(Constants.FUNCTION, Constants.SPEEDDIAL);
                gotoActivity(intent);
            }
        });

        Button delayButton = (Button)findViewById(R.id.btn9_delay);
        delayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemSettingsActivity.this, DelayActivity.class);
                gotoActivity(intent);
            }
        });

        Button zoneButton = (Button)findViewById(R.id.btn10_zone);
        zoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemSettingsActivity.this, ZoneGridActivity.class);
                gotoActivity(intent);
            }
        });
    }

    private void gotoActivity(Intent i) {
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
