package com.example.freeyourdebt;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.CellSignalStrength;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    Button btnLogin,btnCancel;

    EditText userNameInput,passInput;

    String userName,userPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        userNameInput = findViewById(R.id.txtUserNameGetInput);
        passInput = findViewById(R.id.txtPassGetInput);
        Boolean isDeleted = databaseHelper.deleteDebtRec("14");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameInput.setText("");
                passInput.setText("");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = userNameInput.getText().toString();
                userPass = passInput.getText().toString();
                if(userName.length()==0) {
                    Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (userPass.length()==0) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }else {
                    String getPassForDB = LoginCheck(userName);
                    if(getPassForDB.length()==0){
                    }else if (getPassForDB.equals(userPass)){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USERNAME",userName);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this,MainScreenActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Username or Password did not match", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        });
    }
    private String LoginCheck(String userNameForCheck){
        databaseHelper = new DatabaseHelper(this);
        String getPassWord ="" ;
        Cursor cursor = databaseHelper.login(userNameForCheck);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()) {
                getPassWord = cursor.getString(1);
            }
        }else {
            AlertDialog.Builder builderDel = new AlertDialog.Builder(LoginActivity.this);
            builderDel.setMessage("This username is no exist").setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setNegativeButton("Register", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog mDialogDel = builderDel.create();
            mDialogDel.show();
        }

        return getPassWord;
    }
}