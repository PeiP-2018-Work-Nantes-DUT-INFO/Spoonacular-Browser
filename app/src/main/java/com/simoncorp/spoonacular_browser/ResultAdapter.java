package com.simoncorp.spoonacular_browser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simoncorp.spoonacular_browser.api.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultAdapter extends ArrayAdapter<Result> {

    static class Viewholder{
        public TextView time;
        public TextView title;
        public ImageView overview;
    }

    public ResultAdapter(Context context, ArrayList<Result> items) {
        super(context, R.layout.activity_result_activity, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Viewholder viewholder;
        if (row == null) {
            // instanciation d’un View correspondant à notre fichier de layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.simple_recipe_list_item, parent, false);
            viewholder = new Viewholder();
            viewholder.time = row.findViewById(R.id.timeTextView);
            viewholder.title = row.findViewById(R.id.titletextView);
            viewholder.overview = row.findViewById(R.id.overviewImageView);
            row.setTag(viewholder);

        }else {
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