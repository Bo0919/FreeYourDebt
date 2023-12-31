package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
public class ManagementActivity extends AppCompatActivity implements ItemClickListener {
    Button btnReturnManagement;
    RecyclerView recyclerView;
    DebtItemAdapter debtItemAdapter;
    ArrayList<RecycleViewModel> arrayList = new ArrayList<>();

    DatabaseHelper databaseHelper;

    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        btnReturnManagement = findViewById(R.id.btnReturnManagement);
        recyclerView =findViewById(R.id.RecyclerViewList);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userName =sharedPreferences.getString("USERNAME","");
        setUpDebtListModels();
        debtItemAdapter = new DebtItemAdapter(this,arrayList,this);
        recyclerView.setAdapter(debtItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnReturnManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementActivity.this,MainScreenActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpDebtListModels(){
        databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.viewDebtList(userName);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                arrayList.add(new RecycleViewModel(cursor.getString(3),cursor.getString(2),
                        cursor.getString(0),
                        cursor.getString(8),
                        cursor.getString(4),
                        cursor.getString(9)));
            }

        }else{
            Toast.makeText(this,"failed to get the data",Toast.LENGTH_LONG).show();
        }

        }

    @Override
    public void onItemClickListener(int position) {
        Intent intent = new Intent(ManagementActivity.this, ModifyActivity.class);
        intent.putExtra("DID",arrayList.get(position).getDebtID());
        startActivity(intent);
    }
}