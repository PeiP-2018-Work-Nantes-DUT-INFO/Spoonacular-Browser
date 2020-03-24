package com.simoncorp.spoonacular_browser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.simoncorp.spoonacular_browser.api.RecipeInformation;
import com.simoncorp.spoonacular_browser.api.Result;
import com.simoncorp.spoonacular_browser.api.RetrofitClientInstance;
import com.simoncorp.spoonacular_browser.api.SpoonacularService;
import com.simoncorp.spoonacular_browser.model.TypeRecipe;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);
        SpoonacularService service = RetrofitClientInstance.getRetrofitInstance()
                .create(SpoonacularService.class);

        final ImageView recipeImage = findViewById(R.id.recipeImage);
        final TextView recipeTitle = findViewById(R.id.recipeTitle);
        final TextView recipeSteps = findViewById(R.id.stepsTextView);

        Result result = getIntent().getParcelableExtra("result");
        if(result != null) {
            service.getRecipeInformation(result.getId()).enqueue(new Callback<RecipeInformation>() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onResponse(Call<RecipeInformation> call, Response<RecipeInformation> response) {

                    RecipeInformation recipe = response.body();
                    Log.i("VERBOSEHTTP", recipe.getTitle());
                    Picasso.get().load(String.format("https://spoonacular.com/recipeImages/%d-636x393.jpg",
                            recipe.getId())).into(recipeImage);
                    recipeTitle.setText(recipe.getTitle());
                    recipeSteps.setText(recipe.getInstructions());
                }

                @Override
                public void onFailure(Call<RecipeInformation> call, Throwable t) {

                }
            });
        }


    }
}
