package com.jun.luxhomedialer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SystemHelper extends SQLiteOpenHelper {
    private static final String TAG = SystemHelper.class.getSimpleName();
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "system.db";
    public static final String TABLE_NAME = "systems";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "system_name";
    public static final String COLUMN_NUMBER = "number";

    public static final String COLUMN_CALL1 = "CALL1";
    public static final String COLUMN_CALL2 = "CALL2";
    public static final String COLUMN_CALL3 = "CALL3";
    public static final String COLUMN_CALL4 = "CALL4";
    public static final String COLUMN_CALL5 = "CALL5";
    public static final String COLUMN_CALLIMG1 = "IMGCALL1";
    public static final String COLUMN_CALLIMG2 = "IMGCALL2";
    public static final String COLUMN_CALLIMG3 = "IMGCALL3";
    public static final String COLUMN_CALLIMG4 = "IMGCALL4";
    public static final String COLUMN_CALLIMG5 = "IMGCALL5";

    public static final String COLUMN_SMS1 = "SMS1";
    public static final String COLUMN_SMS2 = "SMS2";
    public static final String COLUMN_SMS3 = "SMS3";
    public static final String COLUMN_SMS4 = "SMS4";
    public static final String COLUMN_SMS5 = "SMS5";
    public static final String COLUMN_SMSIMG1 = "IMGSMS1";
    public static final String COLUMN_SMSIMG2 = "IMGSMS2";
    public static final String COLUMN_SMSIMG3 = "IMGSMS3";
    public static final String COLUMN_SMSIMG4 = "IMGSMS4";
    public static final String COLUMN_SMSIMG5 = "IMGSMS5";

    public static final String COLUMN_SPEEDDIAL1 = "SPEEDDIAL1";
    public static final String COLUMN_SPEEDDIAL2 = "SPEEDDIAL2";
    public static final String COLUMN_SPEEDDIAL3 = "SPEEDDIAL3";
    public static final String COLUMN_SPEEDDIAL4 = "SPEEDDIAL4";
    public static final String COLUMN_SPEEDDIAL5 = "SPEEDDIAL5";
    public static final String COLUMN_SPEEDIMG1 = "IMGSPEED1";
    public static final String COLUMN_SPEEDIMG2 = "IMGSPEED2";
    public static final String COLUMN_SPEEDIMG3 = "IMGSPEED3";
    public static final String COLUMN_SPEEDIMG4 = "IMGSPEED4";
    public static final String COLUMN_SPEEDIMG5 = "IMGSPEED5";

    public static final String COLUMN_ZONE1 = "ZONE1";
    public static final String COLUMN_ZONE2 = "ZONE2";
    public static final String COLUMN_ZONE3 = "ZONE3";
    public static final String COLUMN_ZONE4 = "ZONE4";
    public static final String COLUMN_ZONE5 = "ZONE5";
    public static final String COLUMN_ZONE6 = "ZONE6";
    public static final String COLUMN_ZONE7 = "ZONE7";
    public static final String COLUMN_ZONE8 = "ZONE8";
    public static final String COLUMN_ZONE9 = "ZONE9";
    public static final String COLUMN_ZONEIMG1 = "IMGZONE1";
    public static final String COLUMN_ZONEIMG2 = "IMGZONE2";
    public static final String COLUMN_ZONEIMG3 = "IMGZONE3";
    public static final String COLUMN_ZONEIMG4 = "IMGZONE4";
    public static final String COLUMN_ZONEIMG5 = "IMGZONE5";
    public static final String COLUMN_ZONEIMG6 = "IMGZONE6";
    public static final String COLUMN_ZONEIMG7 = "IMGZONE7";
    public static final String COLUMN_ZONEIMG8 = "IMGZONE8";
    public static final String COLUMN_ZONEIMG9 = "IMGZONE9";

    public static final String COLUMN_RFIDTAG1 = "TAG1";
    public static final String COLUMN_RFIDTAG2 = "TAG2";
    public static final String COLUMN_RFIDTAG3 = "TAG3";
    public static final String COLUMN_RFIDTAG4 = "TAG4";
    public static final String COLUMN_RFIDIMG1 = "IMGTAG1";
    public static final String COLUMN_RFIDIMG2 = "IMGTAG2";
    public static final String COLUMN_RFIDIMG3 = "IMGTAG3";
    public static final String COLUMN_RFIDIMG4 = "IMGTAG4";

    public static final String COLUMN_DELAY = "DELAY";
    public static final String COLUMN_VOL = "VOLUME";
    public static final String COLUMN_RINGTIME = "RINGTIME";

    public static final int INDEX_ID = 0;
    public static final int INDEX_NAME = INDEX_ID + 1;
    public static final int INDEX_NUMBER = INDEX_NAME + 1;

    public static final int INDEX_CALL1 = INDEX_NUMBER + 1;
    public static final int INDEX_CALL2 = INDEX_CALL1 + 1;
    public static final int INDEX_CALL3 = INDEX_CALL2 + 1;
    public static final int INDEX_CALL4 = INDEX_CALL3 + 1;
    public static final int INDEX_CALL5 = INDEX_CALL4 + 1;
    public static final int INDEX_CALLIMG1 = INDEX_CALL5 + 1;
    public static final int INDEX_CALLIMG2 = INDEX_CALLIMG1 + 1;
    public static final int INDEX_CALLIMG3 = INDEX_CALLIMG2 + 1;
    public static final int INDEX_CALLIMG4 = INDEX_CALLIMG3 + 1;
    public static final int INDEX_CALLIMG5 = INDEX_CALLIMG4 + 1;

    public static final int INDEX_SMS1 = INDEX_CALLIMG5 + 1;
    public static final int INDEX_SMS2 = INDEX_SMS1 + 1;
    public static final int INDEX_SMS3 = INDEX_SMS2 + 1;
    public static final int INDEX_SMS4 = INDEX_SMS3 + 1;
    public static final int INDEX_SMS5 = INDEX_SMS4 + 1;
    public static final int INDEX_SMSIMG1 = INDEX_SMS5 + 1;
    public static final int INDEX_SMSIMG2 = INDEX_SMSIMG1 + 1;
    public static final int INDEX_SMSIMG3 = INDEX_SMSIMG2 + 1;
    public static final int INDEX_SMSIMG4 = INDEX_SMSIMG3 + 1;
    public static final int INDEX_SMSIMG5 = INDEX_SMSIMG4 + 1;

    public static final int INDEX_SPEEDDIAL1 = INDEX_SMSIMG5 + 1;
    public static final int INDEX_SPEEDDIAL2 = INDEX_SPEEDDIAL1 + 1;
    public static final int INDEX_SPEEDDIAL3 = INDEX_SPEEDDIAL2 + 1;
    public static final int INDEX_SPEEDDIAL4 = INDEX_SPEEDDIAL3 + 1;
    public static final int INDEX_SPEEDDIAL5 = INDEX_SPEEDDIAL4 + 1;
    public static final int INDEX_SPEEDIMG1 = INDEX_SPEEDDIAL5 + 1;
    public static final int INDEX_SPEEDIMG2 = INDEX_SPEEDIMG1 + 1;
    public static final int INDEX_SPEEDIMG3 = INDEX_SPEEDIMG2 + 1;
    public static final int INDEX_SPEEDIMG4 = INDEX_SPEEDIMG3 + 1;
    public static final int INDEX_SPEEDIMG5 = INDEX_SPEEDIMG4 + 1;

    public static final int INDEX_ZONE1 = INDEX_SPEEDIMG5 + 1;
    public static final int INDEX_ZONE2 = INDEX_ZONE1 + 1;
    public static final int INDEX_ZONE3 = INDEX_ZONE2 + 1;
    public static final int INDEX_ZONE4 = INDEX_ZONE3 + 1;
    public static final int INDEX_ZONE5 = INDEX_ZONE4 + 1;
    public static final int INDEX_ZONE6 = INDEX_ZONE5 + 1;
    public static final int INDEX_ZONE7 = INDEX_ZONE6 + 1;
    public static final int INDEX_ZONE8 = INDEX_ZONE7 + 1;
    public static final int INDEX_ZONE9 = INDEX_ZONE8 + 1;
    public static final int INDEX_ZONEIMG1 = INDEX_ZONE9 + 1;
    public static final int INDEX_ZONEIMG2 = INDEX_ZONEIMG1 + 1;
    public static final int INDEX_ZONEIMG3 = INDEX_ZONEIMG2 + 1;
    public static final int INDEX_ZONEIMG4 = INDEX_ZONEIMG3 + 1;
    public static final int INDEX_ZONEIMG5 = INDEX_ZONEIMG4 + 1;
    public static final int INDEX_ZONEIMG6 = INDEX_ZONEIMG5 + 1;
    public static final int INDEX_ZONEIMG7 = INDEX_ZONEIMG6 + 1;
    public static final int INDEX_ZONEIMG8 = INDEX_ZONEIMG7 + 1;
    public static final int INDEX_ZONEIMG9 = INDEX_ZONEIMG8 + 1;

    public static final int INDEX_RFIDTAG1 = INDEX_ZONEIMG9 + 1;
    public static final int INDEX_RFIDTAG2 = INDEX_RFIDTAG1 + 1;
    public static final int INDEX_RFIDTAG3 = INDEX_RFIDTAG2 + 1;
    public static final int INDEX_RFIDTAG4 = INDEX_RFIDTAG3 + 1;
    public static final int INDEX_RFIDIMG1 = INDEX_RFIDTAG4 + 1;
    public static final int INDEX_RFIDIMG2 = INDEX_RFIDIMG1 + 1;
    public static final int INDEX_RFIDIMG3 = INDEX_RFIDIMG2 + 1;
    public static final int INDEX_RFIDIMG4 = INDEX_RFIDIMG3 + 1;

    public static final int INDEX_DELAY = INDEX_RFIDIMG4 + 1;
    public static final int INDEX_VOL = INDEX_DELAY + 1;
    public static final int INDEX_RINGTIME = INDEX_VOL + 1;

    private static final String DB_CREATE = "create table " + TABLE_NAME
            + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_NUMBER + " text not null, "
            + COLUMN_CALL1 + " text, "
            + COLUMN_CALL2 + " text, "
            + COLUMN_CALL3 + " text, "
            + COLUMN_CALL4 + " text, "
            + COLUMN_CALL5 + " text, "
            + COLUMN_CALLIMG1 + " text, "
            + COLUMN_CALLIMG2 + " text, "
            + COLUMN_CALLIMG3 + " text, "
            + COLUMN_CALLIMG4 + " text, "
            + COLUMN_CALLIMG5 + " text, "
            + COLUMN_SMS1 + " text, "
            + COLUMN_SMS2 + " text, "
            + COLUMN_SMS3 + " text, "
            + COLUMN_SMS4 + " text, "
            + COLUMN_SMS5 + " text, "
            + COLUMN_SMSIMG1 + " text, "
            + COLUMN_SMSIMG2 + " text, "
            + COLUMN_SMSIMG3 + " text, "
            + COLUMN_SMSIMG4 + " text, "
            + COLUMN_SMSIMG5 + " text, "
            + COLUMN_SPEEDDIAL1 + " text, "
            + COLUMN_SPEEDDIAL2 + " text, "
            + COLUMN_SPEEDDIAL3 + " text, "
            + COLUMN_SPEEDDIAL4 + " text, "
            + COLUMN_SPEEDDIAL5 + " text, "
            + COLUMN_SPEEDIMG1 + " text, "
            + COLUMN_SPEEDIMG2 + " text, "
            + COLUMN_SPEEDIMG3 + " text, "
            + COLUMN_SPEEDIMG4 + " text, "
            + COLUMN_SPEEDIMG5 + " text, "
            + COLUMN_ZONE1 + " text, "
            + COLUMN_ZONE2 + " text, "
            + COLUMN_ZONE3 + " text, "
            + COLUMN_ZONE4 + " text, "
            + COLUMN_ZONE5 + " text, "
            + COLUMN_ZONE6 + " text, "
            + COLUMN_ZONE7 + " text, "
            + COLUMN_ZONE8 + " text, "
            + COLUMN_ZONE9 + " text, "
            + COLUMN_ZONEIMG1 + " text, "
            + COLUMN_ZONEIMG2 + " text, "
            + COLUMN_ZONEIMG3 + " text, "
            + COLUMN_ZONEIMG4 + " text, "
            + COLUMN_ZONEIMG5 + " text, "
            + COLUMN_ZONEIMG6 + " text, "
            + COLUMN_ZONEIMG7 + " text, "
            + COLUMN_ZONEIMG8 + " text, "
            + COLUMN_ZONEIMG9 + " text, "
            + COLUMN_RFIDTAG1 + " text, "
            + COLUMN_RFIDTAG2 + " text, "
            + COLUMN_RFIDTAG3 + " text, "
            + COLUMN_RFIDTAG4 + " text, "
            + COLUMN_RFIDIMG1 + " text, "
            + COLUMN_RFIDIMG2 + " text, "
            + COLUMN_RFIDIMG3 + " text, "
            + COLUMN_RFIDIMG4 + " text, "
            + COLUMN_DELAY + " integer, "
            + COLUMN_VOL + " integer, "
            + COLUMN_RINGTIME + " integer);";

    public SystemHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version "
                + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
