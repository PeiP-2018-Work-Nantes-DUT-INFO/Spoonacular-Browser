package com.simoncorp.spoonacular_browser.viewmodel;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tylersuehr.chips.Chip;

/**
 * Classe qui représente la donnée métier d'une Chip. Utilisé pour les intolérances,
 * et les ingrédients à exclure
 */
public class ChipItem extends Chip {
    /**
     * le nom de la chip
     */
    private final String name;

    /**
     * le sous contenu de la chip (non utilisé)
     */
    private final String subtitle;

    public ChipItem(String name, String subtitle) {
        this.name = name;
        this.subtitle = subtitle;
    }

    @Nullable
    @Override
    public Object getId() {
        return this;
    }

    /**
     *
     * @return le nom de la chip
     */
    @Override
    @NonNull
    public String getTitle() {
        return name;
    }

    /**
     *
     * @return le sous titre de la chip
     */
    @Nullable
    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public Uri getAvatarUri() {
        return null; // non utilisé
    }

    @Nullable
    @Override
    public Drawable getAvatarDrawable() {
        return null; // non utilisé
    }

}
