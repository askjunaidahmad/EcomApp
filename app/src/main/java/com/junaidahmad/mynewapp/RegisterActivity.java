package com.junaidahmad.mynewapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName,InputPhoneNumber,InputPassword;
    private ProgressDialog loadingBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = findViewById(R.id.btn_register);
        InputName = findViewById(R.id.txt_register_username);
        InputPhoneNumber = findViewById(R.id.txt_register_phone_number);
        InputPassword = findViewById(R.id.txt_register_password);

        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccount();

                //Toast.makeText(RegisterActivity.this, "Wow...!", Toast.LENGTH_LONG).show();


            }
        });

    }

    private void CreateAccount()
    {
        String uname = InputName.getText().toString();
        String uPhoneNumber = InputPhoneNumber.getText().toString();
        String uPassword = InputPassword.getText().toString();

        if(TextUtils.isEmpty(uname))
        {
            Toast.makeText(RegisterActivity.this,"Please Write your Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(uPhoneNumber))
        {
            Toast.makeText(RegisterActivity.this,"Please Write your Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(uPassword))
        {
            Toast.makeText(RegisterActivity.this,"Please Write your Password",Toast.LENGTH_SHORT).show();
        }
        else
            {
                loadingBar.setTitle("Creating Account");
                loadingBar.setMessage("Please Wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                ValidatePhoneNumber(uname,uPhoneNumber,uPassword);

            }
    }

    private void ValidatePhoneNumber(final String uname, final String uPhoneNumber, final String uPassword)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!(snapshot.child("Users").child(uPhoneNumber).exists()))
                {

                    HashMap<String,Object> UserDataMap = new HashMap<>();
                    UserDataMap.put("name",uname);
                    UserDataMap.put("phone",uPhoneNumber);
                    UserDataMap.put("password",uPassword);

                    RootRef.child("Users").child(uPhoneNumber).updateChildren(UserDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,"Registration Created Successfully...!",Toast.LENGTH_LONG).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                        {
                                            loadingBar.dismiss();
                                            Toast.makeText(RegisterActivity.this,"Network Error Please try again",Toast.LENGTH_LONG).show();

                                        }
                                }
                            });

                }
                else
                    {
                        Toast.makeText(RegisterActivity.this,"This User already exist",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);


                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegisterActivity.this,  "Something Error - " + error ,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        });

    }
}