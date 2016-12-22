package com.jun.luxhomedialer.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;
import com.jun.luxhomedialer.adapters.SystemItem;
import com.jun.luxhomedialer.adapters.SystemItemAdapter;
import com.jun.luxhomedialer.database.SystemDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class SystemPickerActivity extends AppCompatActivity {
    private static final String TAG = SystemPickerActivity.class.getSimpleName();
    private SystemDataSource mDataSource;
    private ArrayList<SystemItem> itemList;
    private SystemItemAdapter mAdapter;
    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systempicker);
        mList = (ListView) findViewById(R.id.system_list);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SystemPickerActivity.this, HomeActivity.class);
                if (itemList != null) {
                    intent.putExtra(Constants.KEY_ID, itemList.get(position).getId());
                    intent.putExtra(Constants.KEY_NAME, itemList.get(position).getName());
                }
                gotoActivity(intent);
                SystemItem activeItem = itemList.get(position);
                saveActiveSettingsToPreferences(activeItem);
            }
        });
        registerForContextMenu(mList);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(getResources().getString(R.string.title_sys_sel));

        final Handler handler = new Handler();
        Thread load = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mDataSource == null) {
                        mDataSource = new SystemDataSource(getApplicationContext());
                    }
                    mDataSource.open();
                    itemList = mDataSource.loadSystems();
                    if(mAdapter == null) {
                        mAdapter = new SystemItemAdapter(getApplicationContext(), itemList);
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                } finally {
                    mDataSource.close();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mList.setAdapter(mAdapter);
                            if(itemList.isEmpty()) {
                                Log.e(TAG, "empty list");
                                Intent intent = new Intent(SystemPickerActivity.this, SystemEditorActivity.class);
                                gotoActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });
        load.start();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.system_list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            //menu.setHeaderTitle(itemList.get(info.position).getName());
            String[] menuItems = getResources().getStringArray(R.array.context_menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        SystemItem itemSelected = itemList.get(info.position);

        switch(menuItemIndex) {
            case 0: //edit
                Intent intent = new Intent(SystemPickerActivity.this, SystemEditorActivity.class);
                intent.putExtra(Constants.KEY_ID, itemSelected.getId());
                gotoActivity(intent);
                break;
            case 1: //delete
                try {
                    if (mDataSource == null) {
                        mDataSource = new SystemDataSource(getApplicationContext());
                    }
                    mDataSource.open();
                    int deleteResult = mDataSource.deleteSystemRow(itemSelected.getId());
                    Log.d(TAG, "delete result=" + deleteResult);
                    if(deleteResult > 0) {
                        itemList = mDataSource.loadSystems();
                        mAdapter = null;
                        mAdapter = new SystemItemAdapter(getApplicationContext(), itemList);
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                } catch(NullPointerException e) {
                    e.printStackTrace();
                } finally {
                    if(mDataSource != null) {
                        mDataSource.close();
                    }
                    mList.setAdapter(mAdapter);
                }
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            gotoActivity(new Intent(SystemPickerActivity.this, SystemEditorActivity.class));//, false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoActivity(Intent i) {
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void saveActiveSettingsToPreferences(SystemItem activeItem) {
        try {
            if (mDataSource == null) {
                mDataSource = new SystemDataSource(getApplicationContext());
            }
            mDataSource.open();
            activeItem = mDataSource.loadActiveSettings(activeItem);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(mDataSource != null) {
                mDataSource.close();
            }
        }

        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREFKEY, MODE_PRIVATE).edit();
        Log.d(TAG, "active ID:" + activeItem.getId() + "; number=" + activeItem.getNumber());
        editor.putInt(Constants.KEY_ID, activeItem.getId());
        editor.putString(Constants.NUMBER, activeItem.getNumber());
        for(int i = 0; i < Constants.CALLSMSSUM; i++) {
            Log.d(TAG, "key=" + Constants.CALL + String.valueOf(i + 1));
            Log.d(TAG, "number=" + activeItem.getCallNum(i));
            editor.putString(Constants.CALL + String.valueOf(i + 1), activeItem.getCallNum(i));
            editor.putString(Constants.SMS + String.valueOf(i + 1), activeItem.getSmsNum(i));
            editor.putString(Constants.SPEEDDIAL + String.valueOf(i + 1), activeItem.getSpeeddialNum(i));

            editor.putString(Constants.URICALL + String.valueOf(i+1), activeItem.getCallImageUri(i));
            editor.putString(Constants.URISMS + String.valueOf(i+1), activeItem.getSmsImageUri(i));
            editor.putString(Constants.URISPEED + String.valueOf(i+1), activeItem.getSpeedImageUri(i));
        }

        for(int i = 0; i < Constants.ZONESUM; i++) {
            editor.putString(Constants.ZONE + String.valueOf(i + 1), activeItem.getZoneName(i));
            editor.putString(Constants.URIZONE + String.valueOf(i+1), activeItem.getZoneImageUri(i));
        }

        for(int i = 0; i < Constants.RFIDTAGSUM; i++) {
            editor.putString(Constants.RFIDTAG + String.valueOf(i + 1), activeItem.getTag(i));
            editor.putString(Constants.URIRFID + String.valueOf(i+1), activeItem.getTagImageUri(i));
        }

        editor.putInt(Constants.DELAY, activeItem.getDelay());
        editor.putInt(Constants.VOL, activeItem.getVolume());
        editor.putInt(Constants.RINGTIME, activeItem.getRingtime());

        editor.apply();
    }
}
