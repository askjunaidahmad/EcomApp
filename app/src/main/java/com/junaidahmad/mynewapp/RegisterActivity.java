package com.junaidahmad.mynewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName,InputPhoneNumber,InputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = findViewById(R.id.btn_register);
        InputName = findViewById(R.id.txt_register_username);
        InputPhoneNumber = findViewById(R.id.txt_register_phone_number);
        InputPassword = findViewById(R.id.txt_register_password);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccount();

                Toast.makeText(RegisterActivity.this, "Wow...!", Toast.LENGTH_LONG).show();


            }
        });

    }

    private void CreateAccount()
    {
        String uname = InputName.getText().toString();
        String uPhoneNumber = InputPhoneNumber.getText().toString();
        String uPassword = InputPassword.getText().toString();
    }
}