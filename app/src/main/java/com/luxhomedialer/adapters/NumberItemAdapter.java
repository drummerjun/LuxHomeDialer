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

public class NumberItemAdapter extends BaseAdapter {
    private static final String TAG =  NumberItemAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<NumberItem> mListItems;
    private LayoutInflater mLayoutInflater;

    public NumberItemAdapter(Context context, ArrayList<NumberItem> itemList) {
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
            convertView = mLayoutInflater.inflate(R.layout.layout_numberslistitem, null);
        }
        ImageView thumbnailImage = (ImageView)convertView.findViewById(R.id.img_thumbnail);
        TextView numberTextView = (TextView)convertView.findViewById(R.id.text1);

        NumberItem item = mListItems.get(position);
        Log.d(TAG, "" + item);

        if(item.getNumber().isEmpty()) {
            numberTextView.setText(context.getResources().getString(R.string.hint_number));
        } else {
            numberTextView.setText(item.getNumber());
        }
        String uriString = item.getImageUri();
        if(!uriString.isEmpty()) {
            new LoadBitmapTask().execute(context.getContentResolver(), uriString, thumbnailImage);
        }
        return convertView;
    }


}
