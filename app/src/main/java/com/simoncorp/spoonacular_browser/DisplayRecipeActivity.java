package com.simoncorp.spoonacular_browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.simoncorp.spoonacular_browser.StringItemList.StringItem;
import com.simoncorp.spoonacular_browser.api.AnalyzedInstruction;
import com.simoncorp.spoonacular_browser.api.ExtendedIngredient;
import com.simoncorp.spoonacular_browser.api.RecipeInformation;
import com.simoncorp.spoonacular_browser.api.RetrofitClientInstance;
import com.simoncorp.spoonacular_browser.api.SpoonacularService;
import com.simoncorp.spoonacular_browser.api.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DisplayRecipeActivity extends AppCompatActivity implements IngredientItemFragment.OnListFragmentInteractionListener {
    private TabAdapter adapter;
    private TabLayout tabLayout;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        final ImageView recipeImage = findViewById(R.id.recipeImage);
        final TextView recipeTitle = findViewById(R.id.recipeTitle);
        final TextView recipeSteps = findViewById(R.id.stepsTextView);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        tabLayout = findViewById(R.id.tabLayout);


        RecipeInformation recipe = getIntent().getParcelableExtra("recipe");
        if (recipe != null) {
            Picasso.get().load(String.format("https://spoonacular.com/recipeImages/%d-636x393.jpg",
                    recipe.getId())).into(recipeImage);
            recipeTitle.setText(recipe.getTitle());
            recipeSteps.setText(recipe.getInstructions());

            adapter = new TabAdapter(getSupportFragmentManager());


            List<ExtendedIngredient> ingredients = recipe.getExtendedIngredients();
            List<AnalyzedInstruction> analyzedInstructions = recipe.getAnalyzedInstructions();

            ArrayList<StringItem> ingredientItems = new ArrayList<>();
            if (ingredients != null) {
                for (int i = 0; i < ingredients.size(); i++) {
                    ExtendedIngredient ingredient = ingredients.get(i);
                    ingredientItems.add(new StringItem(String.valueOf(i + 1),
                            ingredient.getName(), ingredient.getAisle()));
                }
            } else {
                ingredientItems.add(new StringItem("1", getString(R.string.info_no_ingredients), ""));
            }

            ArrayList<StringItem> stepsItems = new ArrayList<>();
            if (analyzedInstructions != null && analyzedInstructions.size() > 0) {
                List<Step> steps = analyzedInstructions.get(0).getSteps();
                for (int i = 0; i < steps.size(); i++) {
                    Step step = steps.get(i);
                    stepsItems.add(new StringItem(String.valueOf(i + 1),
                            step.getStep(), ""));
                }
            } else {
                stepsItems.add(new StringItem("1", recipe.getInstructions(),
                        ""));
            }

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

            adapter.addFragment(IngredientItemFragment.newInstance(1, informationItems),
                    getString(R.string.tab_title_info));
            adapter.addFragment(IngredientItemFragment.newInstance(1, ingredientItems),
                    getString(R.string.tab_title_ingredients));
            adapter.addFragment(IngredientItemFragment.newInstance(1, stepsItems),
                    getString(R.string.tab_title_steps));
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            finish();
        }
    }


    @Override
    public void onListFragmentInteraction(StringItem item) {
        Toast.makeText(this, item.content, Toast.LENGTH_SHORT).show();
    }
}
