package com.simoncorp.spoonacular_browser.StringItemList;

import android.os.Parcel;
import android.os.Parcelable;

public class StringItem implements Parcelable{
    public final String id;
    public final String content;
    public final String details;

    public StringItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    protected StringItem(Parcel in) {
        id = in.readString();
        content = in.readString();
        details = in.readString();
    }

    public static final Parcelable.Creator<StringItem> CREATOR = new Parcelable.Creator<StringItem>() {
        @Override
        public StringItem createFromParcel(Parcel in) {
            return new StringItem(in);
        }

        @Override
        public StringItem[] newArray(int size) {
            return new StringItem[size];
        }
    };

    @Override
    public String toString() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(content);
        parcel.writeString(details);
    }
}
