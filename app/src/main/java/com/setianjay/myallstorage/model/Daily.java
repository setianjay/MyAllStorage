package com.setianjay.myallstorage.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Daily implements Parcelable {
    private int id;
    private String title;
    private String content;
    private String date;

    public Daily(){

    }

    protected Daily(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        date = in.readString();
    }

    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel in) {
            return new Daily(in);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate(){
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(date);
    }
}
