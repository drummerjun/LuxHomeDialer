package com.jun.luxhomedialer.adapters;

import android.content.Context;

import com.jun.luxhomedialer.R;

public class SystemItem {
    private Context context;
    private int _id;
    private String name;
    private String number;

    private String[] callNum;
    private String[] smsNum;
    private String[] speeddialNum;
    private String[] zoneNames;
    private String[] tags;

    private String[] callImageUri;
    private String[] smsImageUri;
    private String[] speedImageUri;
    private String[] zoneImageUri;
    private String[] tagImageUri;

    private int delay;
    private int volume;
    private int ringtime;

    public SystemItem(Context context, String name, String number) {
        String zoneRes = context.getResources().getString(R.string.zone);
        String tagRes = context.getResources().getString(R.string.tag);
        this.context = context;
        this.name = name;
        this.number = number;

        callNum =  new String[] {"", "", "", "", ""};
        smsNum = new String[] {"", "", "", "", ""};
        speeddialNum = new String[] {"", "", "", "", ""};
        zoneNames = new String[] { zoneRes + " 1", zoneRes + " 2", zoneRes + " 3",
                zoneRes + " 4", zoneRes + " 5", zoneRes + " 6",
                zoneRes + " 7", zoneRes + " 8", zoneRes + " 9",
        };
        tags = new String[] {tagRes +" 1", tagRes +" 2", tagRes +" 3", tagRes +" 4"};

        callImageUri =  new String[] {"", "", "", "", ""};
        smsImageUri =  new String[] {"", "", "", "", ""};
        speedImageUri =  new String[] {"", "", "", "", ""};
        zoneImageUri =  new String[] {"", "", "", "", "", "", "", "", ""};
        tagImageUri =  new String[] {"", "", "", ""};

        delay = 60;
        volume = R.id.radio0;
        ringtime = 5;
    }

    public void setId(int id) {
        _id = id;
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCallNum(int index) {
        return callNum[index];
    }

    public void setCallNum(String number, int index) {
        callNum[index] = number;
    }

    public String getSmsNum(int index) {
        return smsNum[index];
    }

    public void setSmsNum(String number, int index) {
        smsNum[index] = number;
    }

    public String getSpeeddialNum(int index) {
        return speeddialNum[index];
    }

    public void setSpeeddialNum(String number, int index) {

        speeddialNum[index] = number;
    }

    public String getZoneName(int index) {
        return zoneNames[index];
    }

    public void setZoneName(String name, int index) {
        zoneNames[index] = name;
    }

    public String getTag(int index) {
        return tags[index];
    }

    public void setTag(String name, int index) {
        tags[index] = name;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int vol) {
        volume = vol;
    }

    public int getRingtime() {
        return ringtime;
    }

    public void setRingtime(int ringtime) {
        this.ringtime = ringtime;
    }

    public void setCallImageUri(String uriString, int index) {
        callImageUri[index] = uriString;
    }

    public String getCallImageUri(int index) {
        return callImageUri[index];
    }

    public void setSmsImageUri(String uriString, int index) {
        smsImageUri[index] = uriString;
    }

    public String getSmsImageUri(int index) {
        return smsImageUri[index];
    }

    public void setSpeedImageUri(String uriString, int index) {
        speedImageUri[index] = uriString;
    }

    public String getSpeedImageUri(int index) {
        return speedImageUri[index];
    }

    public void setZoneImageUri(String uriString, int index) {
        zoneImageUri[index] = uriString;
    }

    public String getZoneImageUri(int index) {
        return zoneImageUri[index];
    }

    public void setTagImageUri(String uriString, int index) {
        tagImageUri[index] = uriString;
    }

    public String getTagImageUri(int index) {
        return tagImageUri[index];
    }
}