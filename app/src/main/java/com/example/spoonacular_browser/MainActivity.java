package com.example.spoonacular_browser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText nameRecipe = findViewById(R.id.nameRecipe);
        final Spinner genre = findViewById(R.id.genre);
        final SeekBar numberRecipe = findViewById(R.id.numberRecipe);
        Button searchButton = findViewById(R.id.seearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfRecipe = nameRecipe.getText().toString();
                int numberOfRecipe = numberRecipe.getProgress();
                String genreOfRecipe = genre.getTransitionName();

                TypeRecipe typeRecipe = new TypeRecipe(nameOfRecipe,numberOfRecipe,genreOfRecipe);



            }
        });
    }
}
