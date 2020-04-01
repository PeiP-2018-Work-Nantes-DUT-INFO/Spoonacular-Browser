package com.simoncorp.spoonacular_browser;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.tylersuehr.chips.Chip;

public class ChipItem extends Chip {
    private final String name;
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

    @Override
    public String getTitle() {
        return name;
    }

    @Nullable
    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public Uri getAvatarUri() {
        return null;
    }

    @Nullable
    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }

    // ...other chip methods that are required to implement
}
