package com.jun.luxhomedialer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jun.luxhomedialer.R;

import java.util.ArrayList;

public class SystemItemAdapter extends BaseAdapter{
    private static final String TAG =  SystemItemAdapter.class.getSimpleName();
    private ArrayList<SystemItem> mListItems;
    private LayoutInflater mLayoutInflater;

    public SystemItemAdapter(Context context, ArrayList<SystemItem> itemList) {
        mListItems = itemList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            convertView = mLayoutInflater.inflate(R.layout.layout_systemlistitem, null);
        }
        TextView nameTextView = (TextView)convertView.findViewById(R.id.text1);
        TextView numberTextView = (TextView)convertView.findViewById(R.id.text2);

        SystemItem item = mListItems.get(position);
        Log.d(TAG, "" + item);
        //nameTextView.setTextColor(convertView.getResources().getColor(android.R.color.darker_gray));
        nameTextView.setText(item.getName());
        numberTextView.setText(item.getNumber());

        return convertView;
    }
}
