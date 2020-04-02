/*
 * @author Simon Sassi, Baptiste Batard
 * @version 1.0
 * @date 02/04/2020
 */
package com.simoncorp.spoonacular_browser;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class qui permet de changer l'affichage lorsque l'on change d'onglet dans la DisplayRecipeActivity
 */
public class TabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    /**
     * Constructeur de la classe
     *
     * @param fm
     */
    TabAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    /**
     * Retourn un itme à la position donné
     *
     * @param position position de l'item à retourner
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Ajoute un nouveau fragement
     *
     * @param fragment le fragement à ajouté
     * @param title
     */
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    /**
     * Utilisé pour déterminer le nom de l'onglet
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    /**
     * Retourn le nombre de fragement
     *
     * @return
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
