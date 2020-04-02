/*
 * @author Simon Sassi, Baptiste Batard
 * @version 1.0
 * @date 02/04/2020
 */

package com.simoncorp.spoonacular_browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.simoncorp.spoonacular_browser.viewmodel.StringItem;
import com.simoncorp.spoonacular_browser.repositories.model.recipe.AnalyzedInstruction;
import com.simoncorp.spoonacular_browser.repositories.model.recipe.ExtendedIngredient;
import com.simoncorp.spoonacular_browser.repositories.model.recipe.RecipeInformation;
import com.simoncorp.spoonacular_browser.repositories.model.recipe.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Activité qui sert afficher et a gérer la recette sélectionnée
 */
public class DisplayRecipeActivity extends AppCompatActivity implements IngredientItemFragment.OnListFragmentInteractionListener {


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        /*
         * Permet d'ajouter un boutton de retour dans la bar de status
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ImageView recipeImage = findViewById(R.id.recipeImage);
        final TextView recipeTitle = findViewById(R.id.recipeTitle);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());

        //Récupération des informations transmise par l'activité précédente
        RecipeInformation recipe = getIntent().getParcelableExtra("recipe");

        //Si la recettes reçu n'est pas null alors on affiche toutes les informations qu'elle contient
        if (recipe != null) {

            Picasso.get().load(String.format("https://spoonacular.com/recipeImages/%d-636x393.jpg",
                    recipe.getId())).into(recipeImage);
            recipeTitle.setText(recipe.getTitle());
            // On ajoute les fragments un à un.
            adapter.addFragment(IngredientItemFragment.newInstance(1,
                    getInformationItems(recipe)),
                    getString(R.string.tab_title_info));
            adapter.addFragment(IngredientItemFragment.newInstance(1,
                    getIngredientItems(recipe)),
                    getString(R.string.tab_title_ingredients));
            adapter.addFragment(IngredientItemFragment.newInstance(1,
                    getStepsItems(recipe)),
                    getString(R.string.tab_title_steps));
            viewPager.setAdapter(adapter);
            // On paramètre le tabLayout afin qu'il affiche les bon onglets en fonction des fragment du pager.
            tabLayout.setupWithViewPager(viewPager);
        } else { // On finit l'activité s'il y a un problème.
            finish();
        }
    }

    /**
     * Est utilisé lors du clique sur un élement d'une liste, met dans le presse papier le texte.
     */
    @Override
    public void onListFragmentInteraction(StringItem item) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("SPOONACULAR_RECIPE", item.content);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, getString(R.string.clipboard), Toast.LENGTH_SHORT).show();
    }

    /**
     * Créé le contenu du fragment d'information à partir d'une recette donnée
     * @param recipe La recette
     * @return Liste contenant à chaque entrée une information sur la recette
     */
    private ArrayList<StringItem> getInformationItems(RecipeInformation recipe) {
        ArrayList<StringItem> informationItems = new ArrayList<>();

        informationItems.add(new StringItem("1",
                String.format(getString(R.string.info_source_url),
                        recipe.getSourceUrl()), ""));
        informationItems.add(new StringItem("2",
                String.format(getString(R.string.info_img_url),
                        String.format("https://spoonacular.com/recipeImages/%d-636x393.jpg",
                                recipe.getId())), ""));
        informationItems.add(new StringItem("3",
                String.format(getString(R.string.info_id), recipe.getId()), ""));
        informationItems.add(new StringItem("4",
                String.format(getString(R.string.info_ready_minutes),
                        recipe.getReadyInMinutes()), ""));
        informationItems.add(new StringItem("5",
                String.format(getString(R.string.info_credits),
                        recipe.getCreditsText()), ""));
        informationItems.add(new StringItem("6",
                String.format(getString(R.string.info_score),
                        recipe.getSpoonacularScore()), ""));
        informationItems.add(new StringItem("7",
                String.format(getString(R.string.info_gluten_free),
                        recipe.getGlutenFree()), ""));
        informationItems.add(new StringItem("8",
                String.format(getString(R.string.info_vegan), recipe.getVegan()), ""));
        informationItems.add(new StringItem("9",
                String.format(getString(R.string.info_vegetarian),
                        recipe.getVegetarian()), ""));
        informationItems.add(new StringItem("10",
                String.format(getString(R.string.info_cheap), recipe.getCheap()), ""));
        informationItems.add(new StringItem("11",
                String.format(getString(R.string.info_aggr_likes),
                        recipe.getAggregateLikes()), ""));
        informationItems.add(new StringItem("12",
                String.format(getString(R.string.info_price_serving),
                        recipe.getPricePerServing()), ""));
        informationItems.add(new StringItem("13",
                String.format(getString(R.string.info_dish_type),
                        TextUtils.join(", ", recipe.getDishTypes())), ""));
        return informationItems;
    }

    /**
     * Créé le contenu du fragment d'ingredients à partir de la recette
     * @param recipe La recette
     * @return  Liste contenant à chaque entrée un ingrédient nécessaire à la recette.
     * Cette liste aura qu'un seul élément indiquant qu'il n'y a pas d'ingrédients si n'a aucun ingrédients.
     */
    private ArrayList<StringItem> getIngredientItems(RecipeInformation recipe) {
        List<ExtendedIngredient> ingredients = recipe.getExtendedIngredients();
        ArrayList<StringItem> ingredientItems = new ArrayList<>();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                ExtendedIngredient ingredient = ingredients.get(i);
                ingredientItems.add(new StringItem(String.valueOf(i + 1),
                        ingredient.getName(), ingredient.getAisle()));
            }
        } else {
            ingredientItems.add(new StringItem("", getString(R.string.info_no_ingredients), ""));
        }
        return ingredientItems;
    }

    /**
     * Créé le contenu du fragment d'instructions à partir de la recette
     * @param recipe La recette
     * @return Liste contenant à chaque entrée une instruction.
     * Cette liste aura qu'un seul élément indiquant qu'il n'y a pas d'instructions si la recette n'en a pas.
     */
    private ArrayList<StringItem> getStepsItems(RecipeInformation recipe) {
        List<AnalyzedInstruction> analyzedInstructions = recipe.getAnalyzedInstructions();

        ArrayList<StringItem> stepsItems = new ArrayList<>();
        if (analyzedInstructions != null && analyzedInstructions.size() > 0) {
            List<Step> steps = analyzedInstructions.get(0).getSteps();
            for (int i = 0; i < steps.size(); i++) {
                Step step = steps.get(i);
                stepsItems.add(new StringItem(String.valueOf(i + 1),
                        step.getStep(), ""));
            }
        } else {
            if(recipe.getInstructions() != null) {
                stepsItems.add(new StringItem("1", recipe.getInstructions(),
                        ""));
            } else {
                stepsItems.add(new StringItem("", getString(R.string.info_no_instructions),
                        ""));
            }
        }
        return stepsItems;
    }
    /**
     * Fonction qui ferme l'activité courante si le boutton home est appuié
     * @param item button se trouvant dans la bar de status
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
