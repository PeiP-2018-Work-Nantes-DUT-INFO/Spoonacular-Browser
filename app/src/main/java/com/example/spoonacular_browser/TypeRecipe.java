package com.example.spoonacular_browser;

public class TypeRecipe {
    private String nameOfRecipe;
    private int numberOfRecipe;
    private String genreOfRecip;

    public TypeRecipe(String nameOfRecipe, int numberOfRecipe, String genreOfRecip) {
        this.nameOfRecipe = nameOfRecipe;
        this.numberOfRecipe = numberOfRecipe;
        this.genreOfRecip = genreOfRecip;
    }


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
    public String toString() {
        return "TypeRecipe{" +
                "nameOfRecipe='" + nameOfRecipe + '\'' +
                ", numberOfRecipe=" + numberOfRecipe +
                ", genreOfRecip='" + genreOfRecip + '\'' +
                '}';
    }
}
