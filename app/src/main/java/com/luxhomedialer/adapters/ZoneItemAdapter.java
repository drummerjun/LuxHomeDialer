package com.jun.luxhomedialer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jun.luxhomedialer.LoadBitmapTask;
import com.jun.luxhomedialer.R;

import java.util.ArrayList;

public class ZoneItemAdapter extends BaseAdapter {
    private static final String TAG =  ZoneItemAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<NumberItem> mListItems;
    private LayoutInflater mLayoutInflater;

    public ZoneItemAdapter(Context context, ArrayList<NumberItem> itemList) {
        this.context = context;
        mListItems = itemList;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_zonegriditem, null);
        }
        ImageView thumbnailImage = (ImageView)convertView.findViewById(R.id.img_thumbnail);
        TextView indexView = (TextView)convertView.findViewById(R.id.id1);
        TextView nameTextView = (TextView)convertView.findViewById(R.id.text1);

        indexView.setText(String.valueOf(position + 1) + ". ");
        NumberItem item = mListItems.get(position);
        Log.d(TAG, "" + item);
        nameTextView.setText(item.getName());

        String uriString = item.getImageUri();
        if(!uriString.isEmpty()) {
            new LoadBitmapTask().execute(context.getContentResolver(), uriString, thumbnailImage);
        }
        return convertView;
    }
}
