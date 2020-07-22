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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.junaidahmad.mynewapp.Modal.Users;
import com.junaidahmad.mynewapp.preValent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {


    private EditText InputPhoneNumber , InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminPanelLink , NotAdminPanelLink;

    private com.rey.material.widget.CheckBox chkBoxRememberMe;


    private String parentDBName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.btn_login);
        InputPhoneNumber = findViewById(R.id.txt_login_phone_number);
        InputPassword = findViewById(R.id.txt_login_password);
        chkBoxRememberMe = findViewById(R.id.chk_remember_me);
        AdminPanelLink = findViewById(R.id.admin_panel);
        NotAdminPanelLink = findViewById(R.id.not_admin_panel);

        Paper.init(this);

        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        AdminPanelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButton.setText("Admin Login");
                AdminPanelLink.setVisibility(View.INVISIBLE);
                NotAdminPanelLink.setVisibility(View.VISIBLE);
                parentDBName = "Admins";

            }
        });
        NotAdminPanelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButton.setText("Login");
                AdminPanelLink.setVisibility(View.VISIBLE);
                NotAdminPanelLink.setVisibility(View.INVISIBLE);
                parentDBName = "Users";
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
        if (chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,uPhoneNumber);
            Paper.book().write(Prevalent.UserPassKey,uPassword);
        }

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
                           if(parentDBName.equals("Admins"))
                           {
                               Toast.makeText(LoginActivity.this,"Logged in Successfully",Toast.LENGTH_LONG).show();
                               loadingBar.dismiss();

                               Intent intent = new Intent(LoginActivity.this,AdminCategoryActivity.class);
                               startActivity(intent);
                           }
                           else if (parentDBName.equals("Users"))
                               {
                                   Toast.makeText(LoginActivity.this,"Logged in Successfully",Toast.LENGTH_LONG).show();
                                   loadingBar.dismiss();

                                   Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                   startActivity(intent);
                               }

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