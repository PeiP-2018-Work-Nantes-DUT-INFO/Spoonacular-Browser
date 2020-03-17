package com.simoncorp.spoonacular_browser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.simoncorp.spoonacular_browser.api.RetrofitClientInstance;
import com.simoncorp.spoonacular_browser.api.SearchResults;
import com.simoncorp.spoonacular_browser.api.SpoonacularService;
import com.simoncorp.spoonacular_browser.model.TypeRecipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class ResultActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_activity);
        SpoonacularService service = RetrofitClientInstance.getRetrofitInstance()
                .create(SpoonacularService.class);
        TypeRecipe query = getIntent().getParcelableExtra("query");

        if (query != null) {
            service.searchRecipes(query.getNameOfRecipe(),
                    query.getGenreOfRecip(),
                    null,
                    null)
                    .enqueue(new Callback<SearchResults>() {
                        @Override
                        @EverythingIsNonNull
                        public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                            Toast.makeText(ResultActvity.this,
                                    String.valueOf(response.body().getResults().size()),
                                    Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        @EverythingIsNonNull
                        public void onFailure(Call<SearchResults> call, Throwable t) {
                            Log.e("E", "Bad", t);
                        }
                    });
        }
    }
}
