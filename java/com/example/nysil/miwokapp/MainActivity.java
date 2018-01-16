package com.example.nysil.miwokapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView numbersTextview, familyTextview, colorsTextview, phrasesTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void openNumbersActivity(View view) {

        Intent intent=new Intent(this, NumbersActivity.class);
        startActivity(intent);
    }

    public void openFamilyActivity(View view) {
        Intent intent=new Intent(this, FamilyActivity.class);
        startActivity(intent);
    }

    public void openColorsActivity(View view) {
        Intent intent=new Intent(this, ColorsActivity.class);
        startActivity(intent);
    }

    public void openPhrasesActivity(View view) {
        Intent intent=new Intent(this, PhrasesActivity.class);
        startActivity(intent);
    }
}
