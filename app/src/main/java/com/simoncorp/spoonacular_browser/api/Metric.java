
package com.simoncorp.spoonacular_browser.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metric implements Parcelable {

    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("unitShort")
    @Expose
    private String unitShort;
    @SerializedName("unitLong")
    @Expose
    private String unitLong;

    protected Metric(Parcel in) {
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
        unitShort = in.readString();
        unitLong = in.readString();
    }

    public static final Creator<Metric> CREATOR = new Creator<Metric>() {
        @Override
        public Metric createFromParcel(Parcel in) {
            return new Metric(in);
        }

        @Override
        public Metric[] newArray(int size) {
            return new Metric[size];
        }
    };

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    public String getUnitLong() {
        return unitLong;
    }

    public void setUnitLong(String unitLong) {
        this.unitLong = unitLong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (amount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(amount);
        }
        parcel.writeString(unitShort);
        parcel.writeString(unitLong);
    }
}
