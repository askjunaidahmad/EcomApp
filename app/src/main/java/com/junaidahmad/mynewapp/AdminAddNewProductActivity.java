package com.junaidahmad.mynewapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName;
    private Button ProductSaveButton;
    private EditText InputProductName,InputProductDesc,InputProductPrice;
    private ImageView ImgProduct;

    private Uri ImgUri;

    private static final int GalleryPic = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);


        CategoryName = getIntent().getExtras().get("category").toString();

        ProductSaveButton = findViewById(R.id.btn_save_product);
        InputProductName = findViewById(R.id.txt_product_name);
        InputProductDesc = findViewById(R.id.txt_product_desc);
        InputProductPrice =  findViewById(R.id.txt_product_price);
        ImgProduct = findViewById(R.id.select_product_image);

        ImgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenGallery();
            }
        });


    }

    private void OpenGallery()
    {
        Intent GalleryIntent = new Intent();
        GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent,GalleryPic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPic && resultCode == RESULT_OK && data != null)
        {
            ImgUri = data.getData();
            ImgProduct.setImageURI(ImgUri);
        }

    }
}