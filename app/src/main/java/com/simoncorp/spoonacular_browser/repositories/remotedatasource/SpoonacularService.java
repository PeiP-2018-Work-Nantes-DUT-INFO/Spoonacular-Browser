package com.simoncorp.spoonacular_browser.repositories.remotedatasource;

import androidx.annotation.Nullable;

import com.simoncorp.spoonacular_browser.repositories.model.AutocompleteResult;
import com.simoncorp.spoonacular_browser.repositories.model.recipe.RecipeInformation;
import com.simoncorp.spoonacular_browser.repositories.model.search.SearchResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularService {
    /**
     * Search for a recipe
     *
     * @param query                The (natural language) recipe search query.
     * @param cuisine              The cuisine(s) of the recipes. One or more comma separated. See a full list of supported cuisines.
     * @param offset               The number of results to skip (between 0 and 900).
     * @param limit                The number of results to return (between 1 and 100).
     * @param diet                 The diet for which the recipes must be suitable. See a full list of supported diets.
     * @param excludeIngredients   A comma-separated list of ingredients or ingredient types that the recipes must not contain.
     * @param intolerance          A comma-separated list of intolerances. All recipes returned must not contain ingredients that are not suitable for people with the intolerances entered. See a full list of supported intolerances. Please note: due to the automatic nature of the recipe analysis, the API cannot be 100% accurate in all cases. Please advise your users to seek professional help with medical issues.
     * @param limitLicense         Whether the recipes should have an open license that allows display with proper attribution.
     * @param instructionsRequired Whether the recipes must have instructions.
     * @return A retrofit Call
     */
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

    /**
     * Autocomplete a partial input to suggest possible recipe names.
     *
     * @param query The query to be autocompleted.
     * @param limit The number of results to return (between 1 and 25).
     * @return A retrofit Call
     */
    @GET("/recipes/autocomplete")
    Call<List<AutocompleteResult>> searchRecipesAutocomplete(@Query("query") String query,
                                                             @Nullable @Query("limit") Integer limit);

    /**
     * Use a recipe id to get full information about a recipe, such as ingredients, nutrition, diet and allergen information, etc.
     *
     * @param recipeId The id of the recipe.
     * @return A retrofit Call
     */
    @GET("/recipes/{id}/information")
    Call<RecipeInformation> getRecipeInformation(@Path("id") Integer recipeId);

}
