/*
 * @author Simon Sassi, Baptiste Batard
 * @version 1.0
 * @date 02/04/2020
 */

package com.simoncorp.spoonacular_browser.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Classe qui représente une ligne dans l'affichage d'une recette. Utilisé dans la RecyclerView,
 * pour le fragment des informations d'une recette, des ingrédients ou des instructions
 */
public class StringItem implements Parcelable{
    public final String id;
    public final String content;
    public final String details;

    /**
     * Créer une instance de StringItem
     * @param id Identifiant qui va apparaître à gauche de la ligne
     * @param content Contenu d'une ligne
     * @param details Sous contenu d'une ligne (non utilisé)
     */
    public StringItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    /**
     * Constructeur de la classe qui prend un objet parsé
     */
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

    /**
     * Retourne l'objet sous forme de chaîne de caratères
     */
    @Override
    @NonNull
    public String toString() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parse l'objet
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(content);
        parcel.writeString(details);
    }
}
