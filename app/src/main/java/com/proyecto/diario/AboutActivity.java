package com.proyecto.diario;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button btnBack = (Button) findViewById(R.id.backButton);

        btnBack.setOnClickListener(view -> onBackPressed());

    }
}