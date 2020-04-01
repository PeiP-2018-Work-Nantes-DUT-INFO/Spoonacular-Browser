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

import com.simoncorp.spoonacular_browser.api.AutocompleteResult;
import com.simoncorp.spoonacular_browser.api.RetrofitClientInstance;
import com.simoncorp.spoonacular_browser.api.SpoonacularService;
import com.simoncorp.spoonacular_browser.model.TypeRecipe;
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

        List<String> suggestions = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, suggestions);
        autocomplete.setAdapter(adapter);

        autocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

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

        numberRecipe.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameOfRecipe = autocomplete.getText().toString();
                int numberOfRecipe = numberRecipe.getProgress() + 1;
                String genreOfRecipe = genre.getSelectedItem().toString();
                boolean instructionsRequired = instructionsRequiredSwitch.isChecked();
                boolean limitLicense = limitLicenseSwitch.isChecked();
                String diet = dietSpinner.getSelectedItem().toString();
                String ingredientsExclude = "";
                String intolerances = "";
                if ("None".equals(genreOfRecipe)) {
                    genreOfRecipe = null;
                }
                if ("None".equals(diet)) {
                    diet = null;
                }
                for (Chip chipIngredient : chipsExcludeIngredients.getSelectedChips()) {
                    ingredientsExclude += chipIngredient.getTitle() + ",";
                }
                for (Chip chipIntolerance : chipsInputIntolerances.getSelectedChips()) {
                    intolerances += chipIntolerance.getTitle() + ",";
                }

                TypeRecipe typeRecipe = new TypeRecipe(nameOfRecipe,
                        numberOfRecipe,
                        genreOfRecipe,
                        diet,
                        intolerances,
                        ingredientsExclude,
                        limitLicense,
                        instructionsRequired);

                Intent goToResult = new Intent(MainActivity.this, ResultActivity.class);
                goToResult.putExtra("query", typeRecipe);
                startActivity(goToResult);

            }
        });

        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            hideGroup.setVisibility(View.GONE);
                        } else {
                            hideGroup.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private List<ChipItem> getChipsByArrayId(int id) {
        String[] intolerances = getResources().getStringArray(id);

        ArrayList<ChipItem> chips = new ArrayList<>(intolerances.length);

        for (String intolerance : intolerances) {
            chips.add(new ChipItem(intolerance, ""));
        }
        return chips;
    }
}
