package com.junaidahmad.mynewapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.junaidahmad.mynewapp.Modal.Users;

public class LoginActivity extends AppCompatActivity {


    private EditText InputPhoneNumber , InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private String parentDBName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.btn_login);
        InputPhoneNumber = findViewById(R.id.txt_login_phone_number);
        InputPassword = findViewById(R.id.txt_login_password);

        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

    }


    private void LoginUser()
    {
        String uPhoneNumber = InputPhoneNumber.getText().toString();
        String uPassword = InputPassword.getText().toString();

        if(TextUtils.isEmpty(uPhoneNumber))
        {
            Toast.makeText(LoginActivity.this,"Please Write your Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(uPassword))
        {
            Toast.makeText(LoginActivity.this,"Please Write your Password",Toast.LENGTH_SHORT).show();
        }
        else
            {
                loadingBar.setTitle("Login");
                loadingBar.setMessage("Please wait...!");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                AllowAccessToAccount(uPhoneNumber,uPassword);

            }
    }

    private void AllowAccessToAccount(final String uPhoneNumber, final String uPassword)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.child(parentDBName).child(uPhoneNumber).exists())
                {
                    Users usersData = snapshot.child(parentDBName).child(uPhoneNumber).getValue(Users.class);

                    if(usersData.getPhone().equals(uPhoneNumber))
                    {
                        if(usersData.getPassword().equals(uPassword))
                        {
                            Toast.makeText(LoginActivity.this,"Loggedin Successfully",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);

                        }
                        else
                            {
                                Toast.makeText(LoginActivity.this,"Password is Incorrect",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                    }

                }
                else
                    {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this,"User not exist please create new account",Toast.LENGTH_SHORT).show();

                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}