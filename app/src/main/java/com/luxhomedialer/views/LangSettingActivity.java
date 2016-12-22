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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;

import java.util.ArrayList;

public class LangSettingActivity extends AppCompatActivity {
    private static final String TAG = LangSettingActivity.class.getSimpleName();
    //private static final String[] mLanguages = {"English", "Español", "繁體中文", "简体中文"};
    //private static final String[] mLangCodes = {"0001", "0034", "0886", "0086"};
    //private static final int[] mDialogMessages = {R.string.msg_lang_english,
            //R.string.msg_lang_espanol, R.string.msg_lang_tw, R.string.msg_lang_cn};
    private static final String[] mLanguages = {"English"};
    private static final String[] mLangCodes = {"0001"};
    private static final int[] mDialogMessages = {R.string.msg_lang_english};
    private ArrayList<String> mLangList;

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
        setContentView(R.layout.activity_language_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView langListView = (ListView)findViewById(R.id.lang_list);
        mLangList = new ArrayList<String>();
        for(int i = 0; i < mLanguages.length; i++) {
            mLangList.add(mLanguages[i]);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mLangList);
        langListView.setAdapter(arrayAdapter);
        langListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selectedLanguageCode = mLangCodes[position];
                int selectedDialogMsg = mDialogMessages[position];

                new AlertDialog.Builder(new ContextThemeWrapper(LangSettingActivity.this, R.style.AlertDialogTheme))
                        .setIcon(R.drawable.icon_warn)
                        .setMessage(selectedDialogMsg)
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
