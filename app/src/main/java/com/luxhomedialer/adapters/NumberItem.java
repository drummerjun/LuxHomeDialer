package com.jun.luxhomedialer.adapters;

public class NumberItem {
    private String imageUri;
    private String name;
    private String number;

    public NumberItem(String number) {
        imageUri = "";
        name = "";
        this.number = number;
    }

    public NumberItem(String uri, String number) {
        imageUri = uri;
        name = "";
        this.number = number;
    }

    public NumberItem(String uri, String name, String number) {
        imageUri = uri;
        this.name = name;
        this.number = number;
    }

    public void setImageUri(String uri) {
        imageUri = uri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
