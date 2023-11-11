package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class AddActivity extends AppCompatActivity {
    Button btnCal,btnAdd2Record,btnReturnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        btnCal = findViewById(R.id.btnCal);
        btnAdd2Record = findViewById(R.id.btnAdd2Record);
        btnReturnAdd = findViewById(R.id.btnReturnAdd);

        btnReturnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainScreenActivity.class);
                startActivity(intent);
            }
        });
    }

}