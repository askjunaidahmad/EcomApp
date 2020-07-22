package com.junaidahmad.mynewapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.junaidahmad.mynewapp.Modal.Users;
import com.junaidahmad.mynewapp.preValent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinNowButton, loginButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowButton = findViewById(R.id.btn_join_now);
        loginButton = findViewById(R.id.btn_main_login);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPassKey = Paper.book().read(Prevalent.UserPassKey);

        if(UserPhoneKey != "" && UserPassKey != "")
        {
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPassKey))
            {
                AllowAccess(UserPhoneKey,UserPassKey);

                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait...!");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
        {

        }

    }

    private void AllowAccess(final String uPhoneNumber, final String uPassword)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.child("Users").child(uPhoneNumber).exists())
                {
                    Users usersData = snapshot.child("Users").child(uPhoneNumber).getValue(Users.class);

                    if(usersData.getPhone().equals(uPhoneNumber))
                    {
                        if(usersData.getPassword().equals(uPassword))
                        {
                            Toast.makeText(MainActivity.this,"Loggedin Successfully",Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Password is Incorrect",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }

                }
                else
                {
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this,"User not exist please create new account",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}