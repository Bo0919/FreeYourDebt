package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements ItemClickListener{
    Button btnPrint,btnReturnList;
    RecyclerView recyclerView;
    DebtItemAdapter debtItemAdapter;
    ArrayList<RecycleViewModel> arrayList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnPrint = findViewById(R.id.btnPrintList);
        btnReturnList = findViewById(R.id.btnReturnList);
        recyclerView =findViewById(R.id.RecyclerViewListList);
        setUpDebtListModels();
        debtItemAdapter = new DebtItemAdapter(this,arrayList,this);
        recyclerView.setAdapter(debtItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnReturnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,MainScreenActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setUpDebtListModels(){
        databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.viewDebtList();
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
        Intent intent = new Intent(ListActivity.this, ReminderActivity.class);
        intent.putExtra("DID",arrayList.get(position).getDebtID());
        startActivity(intent);
    }
}