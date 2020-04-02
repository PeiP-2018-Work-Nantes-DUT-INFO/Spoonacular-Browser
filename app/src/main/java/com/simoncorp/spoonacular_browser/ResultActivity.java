/*
 * @author Simon Sassi, Baptiste Batard
 * @version 1.0
 * @date 02/04/2020
 */

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

import com.simoncorp.spoonacular_browser.repositories.model.recipe.RecipeInformation;
import com.simoncorp.spoonacular_browser.repositories.model.search.Result;
import com.simoncorp.spoonacular_browser.repositories.remotedatasource.RetrofitClientInstance;
import com.simoncorp.spoonacular_browser.repositories.model.search.SearchResults;
import com.simoncorp.spoonacular_browser.repositories.remotedatasource.SpoonacularService;
import com.simoncorp.spoonacular_browser.viewmodel.TypeRecipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

/**
 * Activité qui sert afficher et a géré la liste des recettes
 */
public class ResultActivity extends AppCompatActivity {

    private ResultAdapter resultAdapter;
    private SpoonacularService service;
    private TypeRecipe query;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_activity);

        /*
         * Permet d'ajouter un boutton de retour dans la bar de status
         */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        //Création d'une instance de rétrofit
        service = RetrofitClientInstance.getRetrofitInstance()
                .create(SpoonacularService.class);
        query = getIntent().getParcelableExtra("query");
        ArrayList<Result> myArrayList = new ArrayList<>();
        this.resultAdapter = new ResultAdapter(this, myArrayList);
        final ListView listView = findViewById(R.id.resultListView);
        this.progress = findViewById(R.id.progressBar);

        listView.setAdapter(resultAdapter);
        /*
         * peremet de rechercher dans l'api pour effectuer un scroll infinie
         */
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
        /*
         * Gestion d'un click sur un item de la list
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Result recipe = resultAdapter.getItem(position);
                progress.setVisibility(View.VISIBLE);
                // Empèche l'utilisateur de clicker a nouveau tant que la page n'est pas charger
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                service.getRecipeInformation(recipe.getId())
                        .enqueue(new Callback<RecipeInformation>() {
                            /**
                             * En cas de succès de la requette on instancie une variable de la
                             * classe RecipeInformation dans laquelle on les informations récupérer
                             * pour les transmettre au l'activité suivante.
                             */
                            @SuppressLint("DefaultLocale")
                            @Override
                            public void onResponse(Call<RecipeInformation> call,
                                                   Response<RecipeInformation> response) {
                                progress.setVisibility(View.INVISIBLE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                RecipeInformation recipe = response.body();
                                //Si le resultat est null on termine l'activité avec un message d'erreur
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

                            /**
                             * Affiche un toast en cas d'erreur lors de la recherche de recettes
                             */
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

    /**
     * Effectue une requète sur l'apiour obtenir le nombre de recettes corespondant au spécification
     * demander par l'utilisateur sur la page précédente
     *
     * @param offset nombre de recettes que l'on ne vas pas recherhcher, utiliser surtout pour le
     *               scroll inifnie
     */
    private void loadNextDataFromApi(final int offset) {
        progress.setVisibility(View.VISIBLE);
        service.searchRecipes(query.getNameOfRecipe(),
                query.getGenreOfRecip(),
                offset,
                query.getNumberOfRecipe(),
                query.getDiet(),
                query.getIngredientsExcludePayload(),
                query.getIntolerancesPayload(),
                query.isLimitLicense(),
                query.isIncludeInstructions()
        )
                .enqueue(new Callback<SearchResults>() {
                    /**
                     * Ajoute les recettes trouver dans la liste de résultat qui est affiché
                     */
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<SearchResults> call,
                                           Response<SearchResults> response) {
                        progress.setVisibility(View.INVISIBLE);
                        if (response.body() != null) {
                            resultAdapter.addAll(response.body().getResults());
                        } else {
                            Toast.makeText(ResultActivity.this, response.raw().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    /**
                     * En cas d'échec on affiche un toast pour dire qu'il y a eu un problème
                     */
                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<SearchResults> call, Throwable t) {
                        Toast.makeText(ResultActivity.this,
                                "Something bad happened", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     *
     * Fonction qui ferme l'activité courante si le boutton home est sélectionné
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
