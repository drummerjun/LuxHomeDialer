package com.jun.luxhomedialer.views;

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

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;

public class AlarmActivity extends AppCompatActivity {
    private static final String TAG = AlarmActivity.class.getSimpleName();

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
        setContentView(R.layout.activity_alarmsetting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button armButton = (Button)findViewById(R.id.btn_arm);
        armButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(AlarmActivity.this, R.style.AlertDialogTheme))
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
                new AlertDialog.Builder(new ContextThemeWrapper(AlarmActivity.this, R.style.AlertDialogTheme))
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

        Button disarmButton = (Button)findViewById(R.id.btn_disarm);
        disarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(new ContextThemeWrapper(AlarmActivity.this, R.style.AlertDialogTheme))
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
    }
}
