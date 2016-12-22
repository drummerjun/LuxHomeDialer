package com.jun.luxhomedialer.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jun.luxhomedialer.Constants;
import com.jun.luxhomedialer.R;
import com.jun.luxhomedialer.adapters.NumberItem;
import com.jun.luxhomedialer.adapters.ZoneItemAdapter;

import java.util.ArrayList;

public class ZoneGridActivity extends AppCompatActivity{
    private static final String TAG = GenericNumbersListAcitivity.class.getSimpleName();
    private ArrayList<NumberItem> itemList;
    private ZoneItemAdapter mAdapter;

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
        setContentView(R.layout.activity_zonegrid);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(itemList == null) {
            itemList = new ArrayList<NumberItem>();
        }
        if(mAdapter == null) {
            mAdapter = new ZoneItemAdapter(ZoneGridActivity.this, itemList);
        }

        SharedPreferences pref = getSharedPreferences(Constants.PREFKEY, Context.MODE_PRIVATE);
        String zones[] = new String[Constants.ZONESUM];
        String uriStrings[] = new String[Constants.ZONESUM];
        Resources res = getResources();
        itemList.clear();
        for(int i = 0; i < Constants.ZONESUM; i++) {
            String text = String.format(res.getString(R.string.default_zone), i+1);
            zones[i] = pref.getString(Constants.ZONE + String.valueOf(i+1), text);
            uriStrings[i] = pref.getString(Constants.URIZONE + String.valueOf(i+1), "");
            NumberItem item = new NumberItem(uriStrings[i], zones[i], "");
            itemList.add(item);
        }

        mAdapter = new ZoneItemAdapter(this, itemList);
        GridView grids = (GridView)findViewById(R.id.gridview);
        grids.setAdapter(mAdapter);
        grids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoActivity(position + 1);
            }
        });
    }

    private void gotoActivity(int zoneId) {
        Intent intent = new Intent(ZoneGridActivity.this, ZoneSubActivity.class);
        intent.putExtra(Constants.ZONE, zoneId);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
