/*
 * @author Simon Sassi, Baptiste Batard
 * @version 1.0
 * @date 02/04/2020
 */

package com.simoncorp.spoonacular_browser.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe qui sert à passer à l'activité {@link com.simoncorp.spoonacular_browser.ResultActivity}
 * la recherche de l'utilisateur
 */
public class TypeRecipe implements Parcelable {
    private String nameOfRecipe;
    private int numberOfRecipe;
    private String genreOfRecip;
    private String diet;
    private String intolerancesPayload;
    private String ingredientsExcludePayload;
    private boolean limitLicense = false;
    private boolean includeInstructions = false;

    /**
     * Créer une instance de TypeRecipe
     *
     * @param nameOfRecipe        Texte de recherche
     * @param numberOfRecipe      Nombre de recette à obtenir à chaque fois
     * @param genreOfRecip        Type de cuisine
     * @param diet                Type de diet
     * @param intolerances        Chaine de caractères listant les intolérances séparés par une virgule
     * @param ingredientsExclude  Chaine de caractères listant les ingrédients à exclure
     *                            séparés par une virgule
     * @param limitLicense        Si est à vrai, n'affichera que des recettes libres
     * @param includeInstructions Si est à vrai, n'affichera que des recettes qui
     *                            contiennent des instructions
     */
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

    /**
     * Constructeur de la class qui prend une recipe parsé.
     */
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

    /**
     * Parse l'objet
     */
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

    /**
     * Retourne le nom de la recette
     */
    public String getNameOfRecipe() {
        return nameOfRecipe;
    }

    /**
     * Modifier le nom de la recette
     */
    public void setNameOfRecipe(String nameOfRecipe) {
        this.nameOfRecipe = nameOfRecipe;
    }

    /**
     * Retourne le temps de préparation de la recette
     */
    public int getNumberOfRecipe() {
        return numberOfRecipe;
    }

    /**
     * Modifie le temps de préparation de la recette
     */
    public void setNumberOfRecipe(int numberOfRecipe) {
        this.numberOfRecipe = numberOfRecipe;
    }

    /**
     * Retourne le genre de la recette
     */
    public String getGenreOfRecip() {
        return genreOfRecip;
    }

    /**
     * Modifie le genre de la recette
     */
    public void setGenreOfRecip(String genreOfRecip) {
        this.genreOfRecip = genreOfRecip;
    }

    /**
     * Retourne le régime auquel appartient la recette
     */
    public String getDiet() {
        return diet;
    }

    /**
     * Modifie le régime de la recette
     */
    public void setDiet(String diet) {
        this.diet = diet;
    }

    /**
     * Retourn l'intolérence défini par l'utilisateur
     */
    public String getIntolerancesPayload() {
        return intolerancesPayload;
    }

    /**
     * Modifie l'intolérances de l'utilisateur
     */
    public void setIntolerancesPayload(String intolerancesPayload) {
        this.intolerancesPayload = intolerancesPayload;
    }

    /**
     * Retourn les ingrédients exclu
     */
    public String getIngredientsExcludePayload() {
        return ingredientsExcludePayload;
    }

    /**
     * Modifie les ingrédients qui sont exclus
     */
    public void setIngredientsExcludePayload(String ingredientsExcludePayload) {
        this.ingredientsExcludePayload = ingredientsExcludePayload;
    }

    /**
     * Retourne si l'utilisateur ne veut que des recettes libre de droit
     */
    public boolean isLimitLicense() {
        return limitLicense;
    }

    /**
     * Modifie si les recette doive être libre de droit
     */
    public void setLimitLicense(boolean limitLicense) {
        this.limitLicense = limitLicense;
    }

    /**
     * Retourn si l'utilisateur veut des recettes avec les instructions
     */
    public boolean isIncludeInstructions() {
        return includeInstructions;
    }

    /**
     * Modifie le fait de recevoir des recettes qui on des instructions
     */
    public void setIncludeInstructions(boolean includeInstructions) {
        this.includeInstructions = includeInstructions;
    }
}
