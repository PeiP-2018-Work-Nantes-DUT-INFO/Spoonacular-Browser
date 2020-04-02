/*
 * @author Simon Sassi, Baptiste Batard
 * @version 1.0
 * @date 02/04/2020
 */
package com.simoncorp.spoonacular_browser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simoncorp.spoonacular_browser.repositories.model.search.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Class adapter qui fait le lien entre la listView et les données
 */
public class ResultAdapter extends ArrayAdapter<Result> {

    /**
     *Class statique qui permet de stocker les informations pour éviter de les rechercerh à chaque
     * chargerment de la page
     */
    static class Viewholder{
        public TextView time;
        public TextView title;
        public ImageView overview;
    }

    /**
     * Constructeur de la class
     * @param context C'est l'activité sur lequel il est appliquer
     * @param items Une liste des éléments qui seront afficher dans la listView
     */
    public ResultAdapter(Context context, ArrayList<Result> items) {
        super(context, R.layout.activity_result_activity, items);
    }

    /**
     * Gère l'affichage de la vue pour la listView
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Viewholder viewholder;
        //Si c'est la première foie que la page est chargé
        if (row == null) {
            // instanciation d’un View correspondant à notre fichier de layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.simple_recipe_list_item, parent, false);
            viewholder = new Viewholder();
            viewholder.time = row.findViewById(R.id.timeTextView);
            viewholder.title = row.findViewById(R.id.titletextView);
            viewholder.overview = row.findViewById(R.id.overviewImageView);
            //On ajoute le viewholder aux tags de la ligne
            row.setTag(viewholder);

        }else {
            //On récupère le viewholder
            viewholder = (Viewholder)row.getTag();
        }
        // personnalisation de la vue
        Result recipe = getItem(position);
        viewholder.time.setText(String.valueOf(recipe.getReadyInMinutes()));
        viewholder.title.setText(recipe.getTitle());
        Picasso.get()
                .load(String.format("https://spoonacular.com/recipeImages/%d-90x90.jpg",
                        recipe.getId()))
                .into(viewholder.overview);
        return row;
    }
}