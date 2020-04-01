package com.simoncorp.spoonacular_browser.api;

import androidx.annotation.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularService {
    @GET("/recipes/search")
    Call<SearchResults> searchRecipes(@Query("query") String query,
                                      @Nullable @Query("cuisine") String cuisine,
                                      @Nullable @Query("offset") Integer offset,
                                      @Nullable @Query("number") Integer limit,
                                      @Nullable @Query("diet") String diet,
                                      @Nullable @Query("excludeIngredients") String excludeIngredients,
                                      @Nullable @Query("intolerances") String intolerance,
                                      @Nullable @Query("limitLicense") Boolean limitLicense,
                                      @Nullable @Query("instructionsRequired") Boolean instructionsRequired);

    @GET("/recipes/autocomplete")
    Call<List<AutocompleteResult>> searchRecipesAutocomplete(@Query("query") String query,
                                                             @Nullable @Query("limit") Integer limit);

    @GET("/recipes/{id}/information")
    Call<RecipeInformation> getRecipeInformation(@Path("id") Integer recipeId);

}
