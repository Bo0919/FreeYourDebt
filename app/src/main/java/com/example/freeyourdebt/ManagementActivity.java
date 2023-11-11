package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementActivity extends AppCompatActivity {
    Button btnModify,btnReturnManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        btnModify = findViewById(R.id.btnModify);
        btnReturnManagement = findViewById(R.id.btnReturnManagement);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementActivity.this,ModifyActivity.class);
                startActivity(intent);
            }
        });

        btnReturnManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementActivity.this,MainScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}