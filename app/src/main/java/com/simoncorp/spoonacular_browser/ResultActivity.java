package com.simoncorp.spoonacular_browser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.simoncorp.spoonacular_browser.api.RecipeInformation;
import com.simoncorp.spoonacular_browser.api.Result;
import com.simoncorp.spoonacular_browser.api.RetrofitClientInstance;
import com.simoncorp.spoonacular_browser.api.SearchResults;
import com.simoncorp.spoonacular_browser.api.SpoonacularService;
import com.simoncorp.spoonacular_browser.model.TypeRecipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class ResultActivity extends AppCompatActivity {

    private ResultAdapter resultAdapter;
    private SpoonacularService service;
    private TypeRecipe query;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_result_activity);
        service = RetrofitClientInstance.getRetrofitInstance()
                .create(SpoonacularService.class);
        query = getIntent().getParcelableExtra("query");
        ArrayList<Result> myArrayList = new ArrayList<>();
        this.resultAdapter = new ResultAdapter(this, myArrayList);
        final ListView listView = findViewById(R.id.resultListView);
        this.progress = findViewById(R.id.progressBar);

        listView.setAdapter(resultAdapter);
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (totalItemsCount < 900) {
                    loadNextDataFromApi(totalItemsCount);
                    return true;
                } else {
                    return false;
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Result recipe = resultAdapter.getItem(position);
                progress.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                service.getRecipeInformation(recipe.getId())
                        .enqueue(new Callback<RecipeInformation>() {
                            @SuppressLint("DefaultLocale")
                            @Override
                            public void onResponse(Call<RecipeInformation> call,
                                                   Response<RecipeInformation> response) {
                                progress.setVisibility(View.INVISIBLE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                RecipeInformation recipe = response.body();
                                if (recipe == null) {
                                    Toast.makeText(ResultActivity.this,
                                            response.raw().toString(), Toast.LENGTH_LONG).show();
                                    finish();
                                }
                                Intent goToDisplayRecipe = new Intent(ResultActivity.this,
                                        DisplayRecipeActivity.class);
                                goToDisplayRecipe.putExtra("recipe", recipe);
                                startActivity(goToDisplayRecipe);

                            }

                            @Override
                            public void onFailure(Call<RecipeInformation> call, Throwable t) {
                                Toast.makeText(ResultActivity.this,
                                        "Something bad happened", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        if (query != null) {
            loadNextDataFromApi(0);
        } else {
            finish();
        }

    }

    private void loadNextDataFromApi(final int offset) {
        progress.setVisibility(View.VISIBLE);
        service.searchRecipes(query.getNameOfRecipe(),
                query.getGenreOfRecip(),
                offset,
                query.getNumberOfRecipe())
                .enqueue(new Callback<SearchResults>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<SearchResults> call,
                                           Response<SearchResults> response) {
                        progress.setVisibility(View.INVISIBLE);
                        if (response.body() != null) {
                            resultAdapter.addAll(response.body().getResults());
                            Toast.makeText(ResultActivity.this,
                                    String.valueOf(resultAdapter.getCount()),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResultActivity.this, response.raw().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<SearchResults> call, Throwable t) {
                        Toast.makeText(ResultActivity.this,
                                "Something bad happened", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
