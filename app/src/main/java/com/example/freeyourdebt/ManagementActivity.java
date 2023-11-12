package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ManagementActivity extends AppCompatActivity implements ItemClickListener {
    Button btnModify,btnReturnManagement;
    RecyclerView recyclerView;
    DebtItemAdapter debtItemAdapter;
    ArrayList<RecycleViewModle> arrayList;
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

    @Override
    public void onItemClickListener(View view, int adapterPosition) {

    }
}