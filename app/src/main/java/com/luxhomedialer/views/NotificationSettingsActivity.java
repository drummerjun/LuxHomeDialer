package com.jun.luxhomedialer.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;

public class NotificationSettingsActivity extends AppCompatActivity{
    private static final String TAG = NotificationSettingsActivity.class.getSimpleName();

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
        setContentView(R.layout.activity_notif);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button callsButton = (Button)findViewById(R.id.btn_calls);
        callsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationSettingsActivity.this,
                        GenericNumbersListAcitivity.class);
                intent.putExtra(Constants.FUNCTION, Constants.CALL);
                gotoActivity(intent);
            }
        });

        Button smsButton = (Button)findViewById(R.id.btn_sms);
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationSettingsActivity.this,
                        GenericNumbersListAcitivity.class);
                intent.putExtra(Constants.FUNCTION, Constants.SMS);
                gotoActivity(intent);
            }
        });
    }

    private void gotoActivity(Intent i) {
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
