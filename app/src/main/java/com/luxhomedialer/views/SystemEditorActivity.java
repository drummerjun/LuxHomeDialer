package com.jun.luxhomedialer.views;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;
import com.jun.luxhomedialer.adapters.SystemItem;
import com.jun.luxhomedialer.database.SystemDataSource;
import com.jun.luxhomedialer.database.SystemHelper;

import java.io.IOException;

public class SystemEditorActivity extends AppCompatActivity {
    private final static String TAG = SystemEditorActivity.class.getSimpleName();
    private EditText sysNameEditText;
    private EditText sysNumEditText;
    private SystemDataSource mDataSource;
    private int defaultValue = -1;
    private int _id = defaultValue;
    private SystemItem existingItem;

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
        setContentView(R.layout.activity_systemeditor);

        Intent intent = getIntent();
        _id = intent.getIntExtra(Constants.KEY_ID, -1);
        sysNameEditText = (EditText)findViewById(R.id.edit_name);
        sysNumEditText = (EditText)findViewById(R.id.edit_number);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(_id > defaultValue) {
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mDataSource == null) {
                            mDataSource = new SystemDataSource(getApplicationContext());
                        }
                        mDataSource.open();
                        existingItem = mDataSource.loadSystemBasicInfo(_id);
                    } catch(IOException e) {
                        e.printStackTrace();
                    } catch(NullPointerException e) {
                        e.printStackTrace();
                    } finally {
                        mDataSource.close();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                sysNameEditText.setText(existingItem.getName());
                                sysNumEditText.setText(existingItem.getNumber());
                            }
                        });
                    }
                }
            }).start();
        }

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sysNameText = sysNameEditText.getText().toString();
                final String sysNumText = sysNumEditText.getText().toString();
                if (sysNameText.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.empty_name),
                            Toast.LENGTH_LONG).show();
                } else if (sysNumText.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.empty_num),
                            Toast.LENGTH_LONG).show();
                } else {
                    final SystemItem sysItem = new SystemItem(getApplicationContext(), sysNameText, sysNumText);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int returnCode = 0;
                            try {
                                if (mDataSource == null) {
                                    mDataSource = new SystemDataSource(getApplicationContext());
                                }
                                mDataSource.open();
                                if(_id > defaultValue) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(SystemHelper.COLUMN_NAME, sysNameText);
                                    contentValues.put(SystemHelper.COLUMN_NUMBER, sysNumText);
                                    returnCode = mDataSource.updateExistingSystemRow(_id, contentValues);
                                    Log.d(TAG, "modify result=" + returnCode);
                                } else {
                                    returnCode = mDataSource.insertSystemtoDB(sysItem);
                                    Log.d(TAG, "insert result=" + returnCode);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            } finally {
                                mDataSource.close();
                            }
                        }
                    }).start();
                    Intent intent  = new Intent(SystemEditorActivity.this, SystemPickerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    gotoActivity(intent);
                }
            }
        });
    }

    private void gotoActivity(Intent i) {
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
