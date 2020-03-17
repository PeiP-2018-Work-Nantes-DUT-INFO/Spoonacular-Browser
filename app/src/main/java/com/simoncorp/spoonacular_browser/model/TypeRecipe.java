package com.simoncorp.spoonacular_browser.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TypeRecipe implements Parcelable {
    private String nameOfRecipe;
    private int numberOfRecipe;
    private String genreOfRecip;

    public TypeRecipe(String nameOfRecipe, int numberOfRecipe, String genreOfRecip) {
        this.nameOfRecipe = nameOfRecipe;
        this.numberOfRecipe = numberOfRecipe;
        this.genreOfRecip = genreOfRecip;
    }


    private TypeRecipe(Parcel in) {
        nameOfRecipe = in.readString();
        numberOfRecipe = in.readInt();
        genreOfRecip = in.readString();
    }

    public static final Creator<TypeRecipe> CREATOR = new Creator<TypeRecipe>() {
        @Override
        public TypeRecipe createFromParcel(Parcel in) {
            return new TypeRecipe(in);
        }

        @Override
        public TypeRecipe[] newArray(int size) {
            return new TypeRecipe[size];
        }
    };

    public String getNameOfRecipe() {
        return nameOfRecipe;
    }

    public void setNameOfRecipe(String nameOfRecipe) {
        this.nameOfRecipe = nameOfRecipe;
    }

    public int getNumberOfRecipe() {
        return numberOfRecipe;
    }

    public void setNumberOfRecipe(int numberOfRecipe) {
        this.numberOfRecipe = numberOfRecipe;
    }

    public String getGenreOfRecip() {
        return genreOfRecip;
    }

    public void setGenreOfRecip(String genreOfRecip) {
        this.genreOfRecip = genreOfRecip;
    }

    @Override
    @NonNull
    public String toString() {
        return "TypeRecipe{" +
                "nameOfRecipe='" + nameOfRecipe + '\'' +
                ", numberOfRecipe=" + numberOfRecipe +
                ", genreOfRecip='" + genreOfRecip + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameOfRecipe);
        dest.writeInt(numberOfRecipe);
        dest.writeString(genreOfRecip);
    }
}
