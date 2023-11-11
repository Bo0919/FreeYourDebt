package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModifyActivity extends AppCompatActivity {
    Button btnCalModify,btnAdd2RecordModify,btnReturnModify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        btnCalModify = findViewById(R.id.btnCalModify);
        btnAdd2RecordModify = findViewById(R.id.btnAdd2RecordModify);
        btnReturnModify = findViewById(R.id.btnReturnModify);

        btnReturnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyActivity.this,ManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}