package com.simoncorp.spoonacular_browser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.simoncorp.spoonacular_browser.api.RecipeInformation;
import com.simoncorp.spoonacular_browser.api.Result;
import com.simoncorp.spoonacular_browser.api.RetrofitClientInstance;
import com.simoncorp.spoonacular_browser.api.SpoonacularService;
import com.simoncorp.spoonacular_browser.model.TypeRecipe;
import com.squareup.picasso.Picasso;

import okhttp3.internal.annotations.EverythingIsNonNull;
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
        final ProgressBar progressRecipe = findViewById(R.id.progressBarRecipe);

        Result result = getIntent().getParcelableExtra("result");
        if (result != null) {
            service.getRecipeInformation(result.getId()).enqueue(new Callback<RecipeInformation>() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onResponse(Call<RecipeInformation> call, Response<RecipeInformation> response) {
                    progressRecipe.setVisibility(View.INVISIBLE);

                    RecipeInformation recipe = response.body();
                    if (recipe == null) {
                        Toast.makeText(DisplayRecipeActivity.this,
                                response.raw().toString(), Toast.LENGTH_LONG).show();
                    }
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
