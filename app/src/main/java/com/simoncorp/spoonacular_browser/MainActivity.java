/*
 * @author Simon Sassi, Baptiste Batard
 * @version 1.0
 * @date 02/04/2020
 */

package com.simoncorp.spoonacular_browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.simoncorp.spoonacular_browser.repositories.model.AutocompleteResult;
import com.simoncorp.spoonacular_browser.repositories.remotedatasource.RetrofitClientInstance;
import com.simoncorp.spoonacular_browser.repositories.remotedatasource.SpoonacularService;
import com.simoncorp.spoonacular_browser.viewmodel.ChipItem;
import com.simoncorp.spoonacular_browser.viewmodel.TypeRecipe;
import com.tylersuehr.chips.Chip;
import com.tylersuehr.chips.ChipsInputLayout;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /*
         * Déclaration des différent champs de recherche de la page d'accueil
         */
        final Group hideGroup = findViewById(R.id.hideGroup);
        final AutoCompleteTextView autocomplete = findViewById(R.id.nameRecipe);
        final ChipsInputLayout chipsInputIntolerances = findViewById(R.id.chipsIntelorances);
        final ChipsInputLayout chipsExcludeIngredients = findViewById(R.id.chipsExcludeIngredients);
        final Spinner genre = findViewById(R.id.genre);
        final Spinner dietSpinner = findViewById(R.id.spinnerDiet);
        final TextView valueOfSeekBar = findViewById(R.id.valueOfSeekBar);
        final SeekBar numberRecipe = findViewById(R.id.numberRecipe);
        final Switch limitLicenseSwitch = findViewById(R.id.switchLimitLicense);
        final Switch instructionsRequiredSwitch = findViewById(R.id.switchInstructionsRequired);
        Button searchButton = findViewById(R.id.seearchButton);


        chipsInputIntolerances.setFilterableChipList(getChipsByArrayId(R.array.intolerance_list));
        chipsExcludeIngredients.setFilterableChipList(getChipsByArrayId(R.array.ingredients_list));

        /*
         * Mise en place de l'autocomplete
         */
        List<String> suggestions = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, suggestions);
        autocomplete.setAdapter(adapter);

        /*
         * Gestion de l'autocomplete
         */
        autocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            /**
             * La recherche s'effectue après que les text est été modifier
             * @param editable Texte tappé
             */
            @Override
            public void afterTextChanged(Editable editable) {
                SpoonacularService service = RetrofitClientInstance.getRetrofitInstance()
                        .create(SpoonacularService.class);
                service.searchRecipesAutocomplete(editable.toString(), 5).enqueue(new Callback<List<AutocompleteResult>>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<List<AutocompleteResult>> call, Response<List<AutocompleteResult>> response) {
                        adapter.clear();
                        List<String> textItems = new ArrayList<>();
                        if (response.body() != null) {
                            for (AutocompleteResult res : response.body()) {
                                textItems.add(res.getTitle());
                            }
                        }
                        adapter.addAll(textItems);
                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<List<AutocompleteResult>> call, Throwable t) {

                    }
                });
            }
        });

        /*
         * Gestion de la barre permettant de choisir le nombre de recettes attendu
         */
        numberRecipe.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * Affiche la valeur choisi dans un champs d'affichage lorsque la barre est modifié
             * @param seekBar la bar de progression concerner
             * @param progress la valeur de progression de la seekbar
             * @param fromUser si ça été modifier par l'utilisateur
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueOfSeekBar.setText(String.valueOf(numberRecipe.getProgress() + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        numberRecipe.setMax(99);
        numberRecipe.setProgress(9);
        /*
         * Gestion de l'appuie sur le button rechercher
         */
        searchButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Gestion du click sur le button
             * @param v Vue cliquée
             */
            @Override
            public void onClick(View v) {

                /*
                 * On récupère les information resnseigner dans les différents champs
                 */
                String nameOfRecipe = autocomplete.getText().toString();
                int numberOfRecipe = numberRecipe.getProgress() + 1;
                String genreOfRecipe = genre.getSelectedItem().toString();
                boolean instructionsRequired = instructionsRequiredSwitch.isChecked();
                boolean limitLicense = limitLicenseSwitch.isChecked();
                String diet = dietSpinner.getSelectedItem().toString();
                StringBuilder ingredientsExclude = new StringBuilder();
                StringBuilder intolerances = new StringBuilder();
                /*
                 *  Si on a sélectionner None, on met le champs de genre à null, ainsi,
                 *  il ne sera pas spécifier dans retrofit et pas dans la reqûete de recherche
                 */
                if ("None".equals(genreOfRecipe)) {
                    genreOfRecipe = null;
                }
                if ("None".equals(diet)) {
                    diet = null;
                }
                for (Chip chipIngredient : chipsExcludeIngredients.getSelectedChips()) {
                    ingredientsExclude.append(chipIngredient.getTitle()).append(",");
                }
                for (Chip chipIntolerance : chipsInputIntolerances.getSelectedChips()) {
                    intolerances.append(chipIntolerance.getTitle()).append(",");
                }
                /*
                 * On instancie une nouvelle variable de TypeRecipe qui contient les informations
                 * qui seront transmise à l'activité suivante
                 */
                TypeRecipe typeRecipe = new TypeRecipe(nameOfRecipe,
                        numberOfRecipe,
                        genreOfRecipe,
                        diet,
                        intolerances.toString(),
                        ingredientsExclude.toString(),
                        limitLicense,
                        instructionsRequired);

                /*
                 * Lancement de l'activité de résultat en lui passant la variable TypeRecipe
                 * précedemment créé
                 */
                Intent goToResult = new Intent(MainActivity.this, ResultActivity.class);
                goToResult.putExtra("query", typeRecipe);
                startActivity(goToResult);

            }
        });

        /*
         * Permet, lorsque que le clavier est ouvert, de cacher les éléments inutiles afin de faire
         * de la place sur l'écran.
         */
        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            // hideGroup contient tout les éléments qui ne sont pas un EditText
                            hideGroup.setVisibility(View.GONE);
                        } else {
                            hideGroup.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * Obtient un tableau de {@link ChipItem} à partir d'une ressource XML de type string-array
     * @param id ID du tableau de chaîne de caractère à obtenir
     * @return Une liste de ChipItem de la même taille que le string-array spécifié par id
     * et possédant chacun en attribut {@link ChipItem#getTitle()} la valeur d'une ligne du tableau
     */
    private List<ChipItem> getChipsByArrayId(int id) {
        String[] strings = getResources().getStringArray(id);

        ArrayList<ChipItem> chips = new ArrayList<>(strings.length);

        for (String stringValue : strings) {
            chips.add(new ChipItem(stringValue, ""));
        }
        return chips;
    }
}
