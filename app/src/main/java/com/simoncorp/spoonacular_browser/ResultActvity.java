package com.simoncorp.spoonacular_browser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class ResultActvity extends AppCompatActivity {
    private ResultAdapter resultAdapter;
    private SpoonacularService service;
    private TypeRecipe query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_activity);
        service = RetrofitClientInstance.getRetrofitInstance()
                .create(SpoonacularService.class);
        query = getIntent().getParcelableExtra("query");
        ArrayList<Result> myArrayList = new ArrayList<>();
        this.resultAdapter = new ResultAdapter(this, myArrayList);
        final ListView listView = findViewById(R.id.resultListView);
        listView.setAdapter(resultAdapter);
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if(totalItemsCount < 900) {
                    loadNextDataFromApi(totalItemsCount);
                    return true;
                } else {
                    return false;
                }
            }
        });
        if (query != null) {
            loadNextDataFromApi(0);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Result recipe = resultAdapter.getItem(position);

                Intent goToDisplayRecipe = new Intent(ResultActvity.this, DisplayRecipeActivity.class);
                goToDisplayRecipe.putExtra("result", recipe);
                startActivity(goToDisplayRecipe);
            }
        });

    }

    private void loadNextDataFromApi(final int offset) {
        final ProgressBar progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);
        service.searchRecipes(query.getNameOfRecipe(),
                query.getGenreOfRecip(),
                offset,
                query.getNumberOfRecipe())
                .enqueue(new Callback<SearchResults>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                        progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(ResultActvity.this,
                                String.valueOf(offset),
                                Toast.LENGTH_SHORT).show();
                        if(response.body() != null) {
                            resultAdapter.addAll(response.body().getResults());
                        } else {
                            Toast.makeText(ResultActvity.this, response.raw().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<SearchResults> call, Throwable t) {
                        Toast.makeText(ResultActvity.this,
                                "Something bad happenned", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
