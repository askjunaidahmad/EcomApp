package com.junaidahmad.mynewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName;
    private TextView TestingText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);


        TestingText = findViewById(R.id.simple_text);
        CategoryName = getIntent().getExtras().get("category").toString();
        TestingText.setText(CategoryName);

    }
}