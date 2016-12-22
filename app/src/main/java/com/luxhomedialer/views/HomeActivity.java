package com.jun.luxhomedialer.views;

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
import android.widget.Button;
import android.widget.LinearLayout;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;

public class HomeActivity extends AppCompatActivity{
    private static final String TAG = HomeActivity.class.getSimpleName();

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
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        Button alarmSettingButton = (Button)findViewById(R.id.btn1_alarm);
        alarmSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AlarmActivity.class);
                gotoActivity(intent);
            }
        });
        */

        Button armButton = (Button)findViewById(R.id.btn_arm);
        armButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(HomeActivity.this, R.style.AlertDialogTheme))
                        .setIcon(R.drawable.icon_warn)
                        .setMessage(R.string.msg_set_arm)
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

        Button partialButton = (Button)findViewById(R.id.btn_parm);
        partialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(HomeActivity.this, R.style.AlertDialogTheme))
                        .setIcon(R.drawable.icon_warn)
                        .setMessage(R.string.msg_set_parm)
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

        LinearLayout disarmButton = (LinearLayout)findViewById(R.id.btn_disarm);
        disarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(HomeActivity.this, R.style.AlertDialogTheme))
                        .setIcon(R.drawable.icon_warn)
                        .setMessage(R.string.msg_set_disarm)
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

        Button inquiryButton = (Button)findViewById(R.id.btn_current);
        inquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(HomeActivity.this, R.style.AlertDialogTheme))
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

        Button twowayButton = (Button)findViewById(R.id.btn_callback);
        twowayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(HomeActivity.this, R.style.AlertDialogTheme))
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

        Button settingsActivityButton = (Button)findViewById(R.id.btn_settings);
        settingsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SystemSettingsActivity.class);
                gotoActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String title = getIntent().getStringExtra(Constants.KEY_NAME);
        if(title != null && !title.isEmpty()) {
            if(getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        }
    }

    private void gotoActivity(Intent i) {
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
