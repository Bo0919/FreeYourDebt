package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Button btnSubmitRegister,btnCancelRegister;

    EditText userNameInput,userPasswordInput;

    String userNameEnter,userPassEnter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseHelper = new DatabaseHelper(this);
        userNameInput = findViewById(R.id.txtUserNameInput);
        userPasswordInput = findViewById(R.id.txtPasswordInput);
        btnSubmitRegister = findViewById(R.id.btnSubmitRegister);
        btnCancelRegister = findViewById(R.id.btnCancelRegister);

        btnSubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameEnter = userNameInput.getText().toString();
                userPassEnter = userPasswordInput.getText().toString();
                if (userNameEnter.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (userPassEnter.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser2DB(userNameEnter, userPassEnter);
                }
            }
        });

        btnCancelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser2DB(String userName,String userPass){

            databaseHelper = new DatabaseHelper(this);
            boolean isUserAdded = databaseHelper.addUserData(userName,userPass);
            if(isUserAdded){
                Toast.makeText(RegisterActivity.this,"Register successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(RegisterActivity.this,"failed to register",Toast.LENGTH_SHORT).show();
            }
    }
}