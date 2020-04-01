package com.simoncorp.spoonacular_browser.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TypeRecipe implements Parcelable {
    private String nameOfRecipe;
    private int numberOfRecipe;
    private String genreOfRecip;
    private String diet;

    private String intolerancesPayload;
    private String ingredientsExcludePayload;
    private boolean limitLicense = false;
    private boolean includeInstructions = false;


    public TypeRecipe(String nameOfRecipe,
                      int numberOfRecipe,
                      String genreOfRecip,
                      String diet,
                      String intolerances,
                      String ingredientsExclude,
                      boolean limitLicense,
                      boolean includeInstructions) {
        this.nameOfRecipe = nameOfRecipe;
        this.numberOfRecipe = numberOfRecipe;
        this.genreOfRecip = genreOfRecip;
        this.diet = diet;
        this.intolerancesPayload = intolerances;
        this.ingredientsExcludePayload = ingredientsExclude;
        this.limitLicense = limitLicense;
        this.includeInstructions = includeInstructions;
    }


    protected TypeRecipe(Parcel in) {
        nameOfRecipe = in.readString();
        numberOfRecipe = in.readInt();
        genreOfRecip = in.readString();
        diet = in.readString();
        intolerancesPayload = in.readString();
        ingredientsExcludePayload = in.readString();
        limitLicense = in.readByte() != 0;
        includeInstructions = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameOfRecipe);
        dest.writeInt(numberOfRecipe);
        dest.writeString(genreOfRecip);
        dest.writeString(diet);
        dest.writeString(intolerancesPayload);
        dest.writeString(ingredientsExcludePayload);
        dest.writeByte((byte) (limitLicense ? 1 : 0));
        dest.writeByte((byte) (includeInstructions ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getIntolerancesPayload() {
        return intolerancesPayload;
    }

    public void setIntolerancesPayload(String intolerancesPayload) {
        this.intolerancesPayload = intolerancesPayload;
    }

    public String getIngredientsExcludePayload() {
        return ingredientsExcludePayload;
    }

    public void setIngredientsExcludePayload(String ingredientsExcludePayload) {
        this.ingredientsExcludePayload = ingredientsExcludePayload;
    }

    public boolean isLimitLicense() {
        return limitLicense;
    }

    public void setLimitLicense(boolean limitLicense) {
        this.limitLicense = limitLicense;
    }

    public boolean isIncludeInstructions() {
        return includeInstructions;
    }

    public void setIncludeInstructions(boolean includeInstructions) {
        this.includeInstructions = includeInstructions;
    }
}
