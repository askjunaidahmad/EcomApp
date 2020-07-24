package com.junaidahmad.mynewapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName,pName,pDesc,pPrice , saveCurrentTime, saveCurrentDate;
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

        ProductSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
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

    private void ValidateProductData()
    {
        pName = InputProductName.getText().toString();
        pDesc = InputProductDesc.getText().toString();
        pPrice = InputProductPrice.getText().toString();

        if(ImgUri == null)
        {
            Toast.makeText(AdminAddNewProductActivity.this,"Please Select Product Image",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pName))
        {
            Toast.makeText(AdminAddNewProductActivity.this,"Please Type Product Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pDesc))
        {
            Toast.makeText(AdminAddNewProductActivity.this,"Please Type Product Description",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pPrice))
        {
            Toast.makeText(AdminAddNewProductActivity.this,"Please Type Product Price",Toast.LENGTH_SHORT).show();
        }
        else
            {
                StoreProductInformation();
            }

    }

    private void StoreProductInformation()
    {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());



    }
}