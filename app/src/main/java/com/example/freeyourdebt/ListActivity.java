package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements ItemClickListener{
    Button btnPrint,btnReturnList;
    RecyclerView recyclerView;
    DebtItemAdapter debtItemAdapter;
    ArrayList<RecycleViewModel> arrayList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        databaseHelper = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userName =sharedPreferences.getString("USERNAME","");
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
                Cursor cursor = databaseHelper.viewDebtList(userName);
                if(cursor.getCount()>0){
                    StringBuilder str = new StringBuilder();
                    if(cursor.getCount()>0){
                    int debtCount =0;
                    Double debtTotalAmount =0.0;
                    Double debtTotalPayAmount =0.0;
                    while (cursor.moveToNext()) {
                        Double debtAmount =0.0;
                        Double debtPayAmount =0.0;
                        str.append("Debt ID: " + cursor.getString(0)+"  ");
                        str.append("Debt Name: " + cursor.getString(1));
                        str.append("\n");
                        str.append("Debt Type: " + cursor.getString(3)+"  ");
                        str.append("Debt Amount: " + cursor.getString(4));
                        str.append("\n");
                        str.append("Debt Rate: " + cursor.getString(5)+"  ");
                        str.append("Debt Terms: " + cursor.getString(6));
                        str.append("\n");
                        str.append("Payment Cycle: " + cursor.getString(8)+"  ");
                        str.append("Payment Amount: " + cursor.getString(9));
                        str.append("\n");
                        str.append("Memo: " + cursor.getString(7));
                        str.append("\n");
                        str.append("\n");
                        debtAmount = Double.parseDouble(cursor.getString(4));
                        debtPayAmount = Double.parseDouble(cursor.getString(9));
                        debtCount = debtCount+1;
                        debtTotalAmount =debtTotalAmount+debtAmount;
                        debtTotalPayAmount =debtTotalPayAmount+debtPayAmount;
                    }
                        str.append("You have "+debtCount+" debt(s), Total amount is "+debtTotalAmount+" and Total pay amount is "+debtTotalPayAmount);
                        PdfGenerator.generatePdfFromText(str.toString());

                    }else{
                    Toast.makeText(ListActivity.this,"failed to get the data",Toast.LENGTH_LONG).show();
                     }
                try{
                    OutputStreamWriter forOut = new OutputStreamWriter(openFileOutput("MyDebtList.txt", MODE_PRIVATE));
                    forOut.write(str + "\n");
                    forOut.close();
                    Toast.makeText(ListActivity.this,"PDF file printed",Toast.LENGTH_LONG).show();

                } catch (Exception e){
                    e.getStackTrace();
                  }
            }


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
        Cursor cursor = databaseHelper.viewDebtList(userName);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                arrayList.add(new RecycleViewModel(cursor.getString(3),
                        cursor.getString(2),
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
        intent.putExtra("debtId",arrayList.get(position).getDebtID());
        intent.putExtra("debtorName",arrayList.get(position).getDebtName());
        intent.putExtra("amount",arrayList.get(position).getDebtAmount());
        intent.putExtra("debtPayCycle",arrayList.get(position).getDebtType());
        intent.putExtra("payAmount",arrayList.get(position).getDebtPayment());
        startActivity(intent);
        /*did not used
        RecycleViewModel selectedDebt = arrayList.get(position);
        Intent reminderIntent = new Intent(ListActivity.this, ReminderActivity.class);
        reminderIntent.putExtra("DID", selectedDebt.getDebtID());
        reminderIntent.putExtra("DebtName", selectedDebt.getDebtName());
        reminderIntent.putExtra("PaymentType", selectedDebt.getDebtPayment());
        reminderIntent.putExtra("DebtCount", selectedDebt.getDebtCount());
        reminderIntent.putExtra("Amount", selectedDebt.getDebtAmount());
        reminderIntent.putExtra("DueDate", selectedDebt.getDueDate());
        startActivity(reminderIntent);*/

    }
}