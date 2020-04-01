package com.simoncorp.spoonacular_browser;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

import com.simoncorp.spoonacular_browser.api.AutocompleteResult;
import com.simoncorp.spoonacular_browser.api.RetrofitClientInstance;
import com.simoncorp.spoonacular_browser.api.SpoonacularService;
import com.simoncorp.spoonacular_browser.model.TypeRecipe;

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

        final AutoCompleteTextView autocomplete = findViewById(R.id.nameRecipe);
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
        final Spinner genre = findViewById(R.id.genre);
        final TextView valueOfSeekBar = findViewById(R.id.valueOfSeekBar);
        final SeekBar numberRecipe = findViewById(R.id.numberRecipe);
        numberRecipe.setMax(99);
        numberRecipe.setProgress(9);

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
        Button searchButton = findViewById(R.id.seearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameOfRecipe = autocomplete.getText().toString();
                int numberOfRecipe = numberRecipe.getProgress() + 1 ;
                String genreOfRecipe = genre.getSelectedItem().toString();

                TypeRecipe typeRecipe = new TypeRecipe(nameOfRecipe, numberOfRecipe, genreOfRecipe);

                Intent goToResult = new Intent(MainActivity.this, ResultActivity.class);
                goToResult.putExtra("query", typeRecipe);
                startActivity(goToResult);

            }
        });
    }
}
