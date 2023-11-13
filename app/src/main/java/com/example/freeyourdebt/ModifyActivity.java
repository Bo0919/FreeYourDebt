package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyActivity extends AppCompatActivity {
    int DID;
    boolean IsFound;
    DatabaseHelper databaseHelper;
    Spinner debtTypeInputModify;
    RadioButton paymentWeeklyModify,paymentMonthlyModify,paymentBiweeklyModify;
    EditText debtNameInputModify,debtAmountInputModify,debtRateInputModify,debtTermsInputModify,debtMemoInputModify;
    TextView showResultModify;
    Button btnCalModify,btnAdd2RecordModify,btnReturnModify;
    String debtName,debtAmount,debtRate,debtTerms,debtMemo,debtType,paymentCycle,paymentResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        databaseHelper = new DatabaseHelper(this);

        debtNameInputModify = findViewById(R.id.txtDebtNameInputModify);
        debtTypeInputModify = findViewById(R.id.spinnerDebtTypeModify);
        debtAmountInputModify = findViewById(R.id.txtAmountInputModify);
        debtRateInputModify = findViewById(R.id.txtInterestInputModify);
        debtTermsInputModify = findViewById(R.id.txtTermInputModify);
        debtMemoInputModify = findViewById(R.id.txtDebtMemoModify);

        paymentWeeklyModify = findViewById(R.id.rbWeeklyModify);
        paymentMonthlyModify = findViewById(R.id.rbMonthlyModify);
        paymentBiweeklyModify = findViewById(R.id.rbBWeeklyModify);

        showResultModify = findViewById(R.id.txtShowPaymentModify);

        btnCalModify = findViewById(R.id.btnCalModify);
        btnAdd2RecordModify = findViewById(R.id.btnAdd2RecordModify);
        btnReturnModify = findViewById(R.id.btnReturnModify);
        DID = Integer.parseInt(getIntent().getStringExtra("DID"));

        Cursor cursor = databaseHelper.viewTheDebt(DID);
        StringBuilder str = new StringBuilder();
        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                debtNameInputModify.setText(cursor.getString(2));
                if (cursor.getString(3).equals("House")) {
                    debtTypeInputModify.setSelection(0);
                } else if (cursor.getString(3).equals("Car")) {
                    debtTypeInputModify.setSelection(1);
                } else if (cursor.getString(3).equals("Invest")) {
                    debtTypeInputModify.setSelection(2);
                } else if (cursor.getString(3).equals("Credit card")) {
                    debtTypeInputModify.setSelection(3);
                } else {
                    debtTypeInputModify.setSelection(4);
                }
                debtAmountInputModify.setText(cursor.getString(4));
                debtRateInputModify.setText(cursor.getString(5));
                debtTermsInputModify.setText(cursor.getString(6));
                debtMemoInputModify.setText(cursor.getString(7));
                if (cursor.getString(8).equals("Weekly")) {
                    paymentWeeklyModify.setChecked(true);
                    showResultModify.setText("Your payment amount is "+cursor.getString(9)+ " per week");
                } else if (cursor.getString(8).equals("Bi-weekly")) {
                    paymentBiweeklyModify.setChecked(true);
                    showResultModify.setText("Your payment amount is "+cursor.getString(9)+ " per Bi-week");
                } else {
                    paymentMonthlyModify.setChecked(true);
                    showResultModify.setText("Your payment amount is "+cursor.getString(9)+ " per Month");
                }
                }
            }else{
                Toast.makeText(this, "failed to get the data", Toast.LENGTH_LONG).show();
            }

        btnReturnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyActivity.this,ManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}