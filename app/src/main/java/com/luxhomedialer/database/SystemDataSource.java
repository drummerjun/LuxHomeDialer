package com.jun.luxhomedialer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jun.luxhomedialer.adapters.SystemItem;

import java.io.IOException;
import java.util.ArrayList;

public class SystemDataSource {
    private static final String TAG = SystemDataSource.class.getSimpleName();
    private Context context;
    private SQLiteDatabase db;
    private SystemHelper helper;
    private String[] summary_col = {SystemHelper.COLUMN_ID, SystemHelper.COLUMN_NAME,
            SystemHelper.COLUMN_NUMBER};

    private String[] columns = {SystemHelper.COLUMN_ID, SystemHelper.COLUMN_NAME,
            SystemHelper.COLUMN_NUMBER
            , SystemHelper.COLUMN_CALL1, SystemHelper.COLUMN_CALL2, SystemHelper.COLUMN_CALL3,
            SystemHelper.COLUMN_CALL4, SystemHelper.COLUMN_CALL5
            , SystemHelper.COLUMN_CALLIMG1, SystemHelper.COLUMN_CALLIMG2, SystemHelper.COLUMN_CALLIMG3,
            SystemHelper.COLUMN_CALLIMG4, SystemHelper.COLUMN_CALLIMG5
            , SystemHelper.COLUMN_SMS1, SystemHelper.COLUMN_SMS2, SystemHelper.COLUMN_SMS3,
            SystemHelper.COLUMN_SMS4, SystemHelper.COLUMN_SMS5
            , SystemHelper.COLUMN_SMSIMG1, SystemHelper.COLUMN_SMSIMG2, SystemHelper.COLUMN_SMSIMG3,
            SystemHelper.COLUMN_SMSIMG4, SystemHelper.COLUMN_SMSIMG5
            , SystemHelper.COLUMN_SPEEDDIAL1, SystemHelper.COLUMN_SPEEDDIAL2,
            SystemHelper.COLUMN_SPEEDDIAL3, SystemHelper.COLUMN_SPEEDDIAL4,
            SystemHelper.COLUMN_SPEEDDIAL5
            , SystemHelper.COLUMN_SPEEDIMG1, SystemHelper.COLUMN_SPEEDIMG2,
            SystemHelper.COLUMN_SPEEDIMG3, SystemHelper.COLUMN_SPEEDIMG4,
            SystemHelper.COLUMN_SPEEDIMG5
            , SystemHelper.COLUMN_ZONE1, SystemHelper.COLUMN_ZONE2, SystemHelper.COLUMN_ZONE3,
            SystemHelper.COLUMN_ZONE4, SystemHelper.COLUMN_ZONE5, SystemHelper.COLUMN_ZONE6,
            SystemHelper.COLUMN_ZONE7, SystemHelper.COLUMN_ZONE8, SystemHelper.COLUMN_ZONE9
            , SystemHelper.COLUMN_ZONEIMG1, SystemHelper.COLUMN_ZONEIMG2, SystemHelper.COLUMN_ZONEIMG3,
            SystemHelper.COLUMN_ZONEIMG4, SystemHelper.COLUMN_ZONEIMG5, SystemHelper.COLUMN_ZONEIMG6,
            SystemHelper.COLUMN_ZONEIMG7, SystemHelper.COLUMN_ZONEIMG8, SystemHelper.COLUMN_ZONEIMG9
            , SystemHelper.COLUMN_RFIDTAG1, SystemHelper.COLUMN_RFIDTAG2,
            SystemHelper.COLUMN_RFIDTAG3, SystemHelper.COLUMN_RFIDTAG4
            , SystemHelper.COLUMN_RFIDIMG1, SystemHelper.COLUMN_RFIDIMG2,
            SystemHelper.COLUMN_RFIDIMG3, SystemHelper.COLUMN_RFIDIMG4
            , SystemHelper.COLUMN_DELAY, SystemHelper.COLUMN_VOL, SystemHelper.COLUMN_RINGTIME
    };

    public SystemDataSource(Context context) {
        this.context = context;
        helper = new SystemHelper(context);
    }

    public void open() throws IOException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public int insertSystemtoDB(SystemItem item) {
        ContentValues values = new ContentValues();
        values.put(SystemHelper.COLUMN_NAME, item.getName().trim());
        values.put(SystemHelper.COLUMN_NUMBER, item.getNumber().trim());

        values.putNull(SystemHelper.COLUMN_CALL1);
        values.putNull(SystemHelper.COLUMN_CALL2);
        values.putNull(SystemHelper.COLUMN_CALL3);
        values.putNull(SystemHelper.COLUMN_CALL4);
        values.putNull(SystemHelper.COLUMN_CALL5);
        values.putNull(SystemHelper.COLUMN_CALLIMG1);
        values.putNull(SystemHelper.COLUMN_CALLIMG2);
        values.putNull(SystemHelper.COLUMN_CALLIMG3);
        values.putNull(SystemHelper.COLUMN_CALLIMG4);
        values.putNull(SystemHelper.COLUMN_CALLIMG5);
        values.putNull(SystemHelper.COLUMN_SMS1);
        values.putNull(SystemHelper.COLUMN_SMS2);
        values.putNull(SystemHelper.COLUMN_SMS3);
        values.putNull(SystemHelper.COLUMN_SMS4);
        values.putNull(SystemHelper.COLUMN_SMS5);
        values.putNull(SystemHelper.COLUMN_SMSIMG1);
        values.putNull(SystemHelper.COLUMN_SMSIMG2);
        values.putNull(SystemHelper.COLUMN_SMSIMG3);
        values.putNull(SystemHelper.COLUMN_SMSIMG4);
        values.putNull(SystemHelper.COLUMN_SMSIMG5);
        values.putNull(SystemHelper.COLUMN_SPEEDDIAL1);
        values.putNull(SystemHelper.COLUMN_SPEEDDIAL2);
        values.putNull(SystemHelper.COLUMN_SPEEDDIAL3);
        values.putNull(SystemHelper.COLUMN_SPEEDDIAL4);
        values.putNull(SystemHelper.COLUMN_SPEEDDIAL5);
        values.putNull(SystemHelper.COLUMN_SPEEDIMG1);
        values.putNull(SystemHelper.COLUMN_SPEEDIMG1);
        values.putNull(SystemHelper.COLUMN_SPEEDIMG1);
        values.putNull(SystemHelper.COLUMN_SPEEDIMG1);
        values.putNull(SystemHelper.COLUMN_SPEEDIMG1);
        values.putNull(SystemHelper.COLUMN_ZONE1);
        values.putNull(SystemHelper.COLUMN_ZONE2);
        values.putNull(SystemHelper.COLUMN_ZONE3);
        values.putNull(SystemHelper.COLUMN_ZONE4);
        values.putNull(SystemHelper.COLUMN_ZONE5);
        values.putNull(SystemHelper.COLUMN_ZONE6);
        values.putNull(SystemHelper.COLUMN_ZONE7);
        values.putNull(SystemHelper.COLUMN_ZONE8);
        values.putNull(SystemHelper.COLUMN_ZONE9);
        values.putNull(SystemHelper.COLUMN_ZONEIMG1);
        values.putNull(SystemHelper.COLUMN_ZONEIMG2);
        values.putNull(SystemHelper.COLUMN_ZONEIMG3);
        values.putNull(SystemHelper.COLUMN_ZONEIMG4);
        values.putNull(SystemHelper.COLUMN_ZONEIMG5);
        values.putNull(SystemHelper.COLUMN_ZONEIMG6);
        values.putNull(SystemHelper.COLUMN_ZONEIMG7);
        values.putNull(SystemHelper.COLUMN_ZONEIMG8);
        values.putNull(SystemHelper.COLUMN_ZONEIMG9);
        values.putNull(SystemHelper.COLUMN_RFIDTAG1);
        values.putNull(SystemHelper.COLUMN_RFIDTAG2);
        values.putNull(SystemHelper.COLUMN_RFIDTAG3);
        values.putNull(SystemHelper.COLUMN_RFIDTAG4);
        values.putNull(SystemHelper.COLUMN_RFIDIMG1);
        values.putNull(SystemHelper.COLUMN_RFIDIMG2);
        values.putNull(SystemHelper.COLUMN_RFIDIMG3);
        values.putNull(SystemHelper.COLUMN_RFIDIMG4);
        values.putNull(SystemHelper.COLUMN_DELAY);
        values.putNull(SystemHelper.COLUMN_VOL);
        values.putNull(SystemHelper.COLUMN_RINGTIME);

        long insertId = db.insert(SystemHelper.TABLE_NAME, null, values);
        return safeLongToInt(insertId);
    }

    public int updateExistingSystemRow(int _id, ContentValues cv) {
        int editResult = db.update(SystemHelper.TABLE_NAME, cv,
//                "_id='" + String.valueOf(_id) + "'", null);
                "_id=?", new String[] {String.valueOf(_id)});
        return editResult;
    }

    public int deleteSystemRow(int _id) {
        int delResult = db.delete(SystemHelper.TABLE_NAME,
                "_id='" + String.valueOf(_id) + "'", null);
        return delResult;
    }

    public int reset() {
        return db.delete(SystemHelper.TABLE_NAME, null, null);
    }

    public ArrayList<SystemItem> loadSystems() {
        ArrayList<SystemItem> itemList = new ArrayList<>();
        Cursor cursor = db.query(SystemHelper.TABLE_NAME, summary_col, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(SystemHelper.INDEX_NAME);
            String number = cursor.getString(SystemHelper.INDEX_NUMBER);
            SystemItem item = new SystemItem(context, name, number);
            item.setId(cursor.getInt(SystemHelper.INDEX_ID));
            itemList.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return itemList;
    }

    public SystemItem loadSystemBasicInfo(int _id) {
        String existingName = "";
        String existingNumber = "";
        String[] columnString = {SystemHelper.COLUMN_ID, SystemHelper.COLUMN_NAME,
                SystemHelper.COLUMN_NUMBER};
        Cursor cursor = db.query(SystemHelper.TABLE_NAME, columnString,
                "_id='" + _id + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            existingName = cursor.getString(SystemHelper.INDEX_NAME);
            existingNumber = cursor.getString(SystemHelper.INDEX_NUMBER);
            cursor.moveToNext();
        }
        cursor.close();
        return new SystemItem(context, existingName, existingNumber);
    }

    public SystemItem loadActiveSettings(SystemItem activeItem) {
        Cursor cursor = db.query(SystemHelper.TABLE_NAME, columns,
                "_id='" + activeItem.getId() + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            activeItem.setCallNum(cursor.getString(SystemHelper.INDEX_CALL1), 0);
            activeItem.setCallNum(cursor.getString(SystemHelper.INDEX_CALL2), 1);
            activeItem.setCallNum(cursor.getString(SystemHelper.INDEX_CALL3), 2);
            activeItem.setCallNum(cursor.getString(SystemHelper.INDEX_CALL4), 3);
            activeItem.setCallNum(cursor.getString(SystemHelper.INDEX_CALL5), 4);
            activeItem.setSmsNum(cursor.getString(SystemHelper.INDEX_SMS1), 0);
            activeItem.setSmsNum(cursor.getString(SystemHelper.INDEX_SMS2), 1);
            activeItem.setSmsNum(cursor.getString(SystemHelper.INDEX_SMS3), 2);
            activeItem.setSmsNum(cursor.getString(SystemHelper.INDEX_SMS4), 3);
            activeItem.setSmsNum(cursor.getString(SystemHelper.INDEX_SMS5), 4);
            activeItem.setSpeeddialNum(cursor.getString(SystemHelper.INDEX_SPEEDDIAL1), 0);
            activeItem.setSpeeddialNum(cursor.getString(SystemHelper.INDEX_SPEEDDIAL2), 1);
            activeItem.setSpeeddialNum(cursor.getString(SystemHelper.INDEX_SPEEDDIAL3), 2);
            activeItem.setSpeeddialNum(cursor.getString(SystemHelper.INDEX_SPEEDDIAL4), 3);
            activeItem.setSpeeddialNum(cursor.getString(SystemHelper.INDEX_SPEEDDIAL5), 4);
            activeItem.setZoneName(cursor.getString(SystemHelper.INDEX_ZONE1), 0);
            activeItem.setZoneName(cursor.getString(SystemHelper.INDEX_ZONE2), 1);
            activeItem.setZoneName(cursor.getString(SystemHelper.INDEX_ZONE3), 2);
            activeItem.setZoneName(cursor.getString(SystemHelper.INDEX_ZONE4), 3);
            activeItem.setZoneName(cursor.getString(SystemHelper.INDEX_ZONE5), 4);
            activeItem.setZoneName(cursor.getString(SystemHelper.INDEX_ZONE6), 5);
            activeItem.setZoneName(cursor.getString(SystemHelper.INDEX_ZONE7), 6);
            activeItem.setZoneName(cursor.getString(SystemHelper.INDEX_ZONE8), 7);
            activeItem.setZoneName(cursor.getString(SystemHelper.INDEX_ZONE9), 8);
            activeItem.setTag(cursor.getString(SystemHelper.INDEX_RFIDTAG1), 0);
            activeItem.setTag(cursor.getString(SystemHelper.INDEX_RFIDTAG2), 1);
            activeItem.setTag(cursor.getString(SystemHelper.INDEX_RFIDTAG3), 2);
            activeItem.setTag(cursor.getString(SystemHelper.INDEX_RFIDTAG4), 3);
            activeItem.setDelay(cursor.getInt(SystemHelper.INDEX_DELAY));
            activeItem.setVolume(cursor.getInt(SystemHelper.INDEX_VOL));
            activeItem.setRingtime(cursor.getInt(SystemHelper.INDEX_RINGTIME));

            activeItem.setCallImageUri(cursor.getString(SystemHelper.INDEX_CALLIMG1), 0);
            activeItem.setCallImageUri(cursor.getString(SystemHelper.INDEX_CALLIMG2), 1);
            activeItem.setCallImageUri(cursor.getString(SystemHelper.INDEX_CALLIMG3), 2);
            activeItem.setCallImageUri(cursor.getString(SystemHelper.INDEX_CALLIMG4), 3);
            activeItem.setCallImageUri(cursor.getString(SystemHelper.INDEX_CALLIMG5), 4);
            activeItem.setSmsImageUri(cursor.getString(SystemHelper.INDEX_SMSIMG1), 0);
            activeItem.setSmsImageUri(cursor.getString(SystemHelper.INDEX_SMSIMG2), 1);
            activeItem.setSmsImageUri(cursor.getString(SystemHelper.INDEX_SMSIMG3), 2);
            activeItem.setSmsImageUri(cursor.getString(SystemHelper.INDEX_SMSIMG4), 3);
            activeItem.setSmsImageUri(cursor.getString(SystemHelper.INDEX_SMSIMG5), 4);
            activeItem.setSpeedImageUri(cursor.getString(SystemHelper.INDEX_SPEEDIMG1), 0);
            activeItem.setSpeedImageUri(cursor.getString(SystemHelper.INDEX_SPEEDIMG2), 1);
            activeItem.setSpeedImageUri(cursor.getString(SystemHelper.INDEX_SPEEDIMG3), 2);
            activeItem.setSpeedImageUri(cursor.getString(SystemHelper.INDEX_SPEEDIMG4), 3);
            activeItem.setSpeedImageUri(cursor.getString(SystemHelper.INDEX_SPEEDIMG5), 4);
            activeItem.setZoneImageUri(cursor.getString(SystemHelper.INDEX_ZONEIMG1), 0);
            activeItem.setZoneImageUri(cursor.getString(SystemHelper.INDEX_ZONEIMG2), 1);
            activeItem.setZoneImageUri(cursor.getString(SystemHelper.INDEX_ZONEIMG3), 2);
            activeItem.setZoneImageUri(cursor.getString(SystemHelper.INDEX_ZONEIMG4), 3);
            activeItem.setZoneImageUri(cursor.getString(SystemHelper.INDEX_ZONEIMG5), 4);
            activeItem.setZoneImageUri(cursor.getString(SystemHelper.INDEX_ZONEIMG6), 5);
            activeItem.setZoneImageUri(cursor.getString(SystemHelper.INDEX_ZONEIMG7), 6);
            activeItem.setZoneImageUri(cursor.getString(SystemHelper.INDEX_ZONEIMG8), 7);
            activeItem.setZoneImageUri(cursor.getString(SystemHelper.INDEX_ZONEIMG9), 8);
            activeItem.setTagImageUri(cursor.getString(SystemHelper.INDEX_RFIDIMG1), 0);
            activeItem.setTagImageUri(cursor.getString(SystemHelper.INDEX_RFIDIMG2), 1);
            activeItem.setTagImageUri(cursor.getString(SystemHelper.INDEX_RFIDIMG3), 2);
            activeItem.setTagImageUri(cursor.getString(SystemHelper.INDEX_RFIDIMG4), 3);

            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return activeItem;
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}
