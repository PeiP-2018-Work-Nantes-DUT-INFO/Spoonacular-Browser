
package com.simoncorp.spoonacular_browser.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Length implements Parcelable {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("unit")
    @Expose
    private String unit;

    protected Length(Parcel in) {
        if (in.readByte() == 0) {
            number = null;
        } else {
            number = in.readInt();
        }
        unit = in.readString();
    }

    public static final Creator<Length> CREATOR = new Creator<Length>() {
        @Override
        public Length createFromParcel(Parcel in) {
            return new Length(in);
        }

        @Override
        public Length[] newArray(int size) {
            return new Length[size];
        }
    };

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (number == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(number);
        }
        parcel.writeString(unit);
    }
}
