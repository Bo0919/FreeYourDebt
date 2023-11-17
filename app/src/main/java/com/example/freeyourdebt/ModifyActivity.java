package com.example.freeyourdebt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import java.text.DecimalFormat;

public class ModifyActivity extends AppCompatActivity {
    int DID;
    String DIDInput;

    DatabaseHelper databaseHelper;
    Spinner debtTypeInputModify;
    RadioButton paymentWeeklyModify,paymentMonthlyModify,paymentBiweeklyModify;
    EditText debtNameInputModify,debtAmountInputModify,debtRateInputModify,debtTermsInputModify,debtMemoInputModify;
    TextView showResultModify;
    Button btnCalModify,btnAdd2RecordModify,btnReturnModify,btnDelModify;
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
        btnDelModify= findViewById(R.id.btnDelModify);
        DID = Integer.parseInt(getIntent().getStringExtra("DID"));
        DIDInput= Integer.toString(DID);
        Cursor cursor = databaseHelper.viewTheDebt(DID);
        if (cursor.getCount() > 0) {
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
                    showResultModify.setText(cursor.getString(9));
                } else if (cursor.getString(8).equals("Bi-weekly")) {
                    paymentBiweeklyModify.setChecked(true);
                    showResultModify.setText(cursor.getString(9));
                } else {
                    paymentMonthlyModify.setChecked(true);
                    showResultModify.setText(cursor.getString(9));
                }
            }
        } else {
            Toast.makeText(this, "failed to get the data", Toast.LENGTH_LONG).show();
        }
        btnCalModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calPayment();
            }
        });

        btnAdd2RecordModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                builder.setMessage("Do you really want to update the data?").setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (showResultModify.getText().length() == 0) {
                            Toast.makeText(ModifyActivity.this,"Payment can not be empty. Do calculating",Toast.LENGTH_SHORT).show();
                            calPayment();
                        } else {
                            Boolean isUpdated = databaseHelper.updateDebtRec(DIDInput, debtName, debtType, debtAmount, debtRate, debtTerms, debtMemo, paymentCycle, showResultModify.getText().toString());
                            if(isUpdated){
                                Toast.makeText(ModifyActivity.this,"update data successfully",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ModifyActivity.this,"failed to update data",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });

        btnDelModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderDel = new AlertDialog.Builder(ModifyActivity.this);
                builderDel.setMessage("Do you really want to delete the data?").setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            Boolean isDeleted = databaseHelper.deleteDebtRec(DIDInput);
                            if(isDeleted){
                                Toast.makeText(ModifyActivity.this,"delete data successfully",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ModifyActivity.this,"failed to delete data",Toast.LENGTH_SHORT).show();
                            }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog mDialogDel = builderDel.create();
                mDialogDel.show();
                    }
        });

        btnReturnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyActivity.this,ManagementActivity.class);
                startActivity(intent);
            }
        });
    }
    private static double calculateLoanPayment(double loanAmount, double annualInterestRate, int loanTermYears, int compoundingPeriodsPerYear) {
        double monthlyInterestRate = (annualInterestRate / 100) / compoundingPeriodsPerYear;
        int totalPayments = loanTermYears * compoundingPeriodsPerYear;
        double discountFactor = calculateDiscountFactor(monthlyInterestRate, totalPayments);

        return (loanAmount * monthlyInterestRate) / (1 - discountFactor);
    }

    private static double calculateDiscountFactor(double monthlyInterestRate, int totalPayments) {
        return Math.pow((1 + monthlyInterestRate), -totalPayments);
    }
    private void calPayment(){
        debtName = debtNameInputModify.getText().toString();
        debtAmount = debtAmountInputModify.getText().toString();
        debtRate = debtRateInputModify.getText().toString();
        debtTerms = debtTermsInputModify.getText().toString();
        debtMemo = debtMemoInputModify.getText().toString();
        debtType = debtTypeInputModify.getSelectedItem().toString();
        paymentResult = "";
        if(debtName.length()==0){
            Toast.makeText(ModifyActivity.this,"Debt Name can not be empty",Toast.LENGTH_SHORT).show();
        } else if (debtAmount.length()==0){
            Toast.makeText(ModifyActivity.this,"Debt Amount can not be empty",Toast.LENGTH_SHORT).show();
        } else if (debtRate.length()==0){
            Toast.makeText(ModifyActivity.this,"Debt Rate can not be empty",Toast.LENGTH_SHORT).show();
        } else if (debtTerms.length()==0){
            Toast.makeText(ModifyActivity.this,"Debt Terms can not be empty",Toast.LENGTH_SHORT).show();
        } else {
            DecimalFormat resultFormat = new DecimalFormat("0.###");
            int termforCal = Integer.parseInt(debtTerms);
            Double amountforCal = Double.parseDouble(debtAmount);
            Double rateforCal = Double.parseDouble(debtRate);
            if (paymentWeeklyModify.isChecked()) {
                double weeklyPayment = calculateLoanPayment(amountforCal, rateforCal, termforCal, 52);
                paymentCycle = "Weekly";
                paymentResult =  resultFormat.format(weeklyPayment);
                showResultModify.setText(paymentResult);
            } else if (paymentBiweeklyModify.isChecked()) {
                double biWeeklyPayment = calculateLoanPayment(amountforCal, rateforCal, termforCal, 26);
                paymentCycle = "Bi-weekly";
                paymentResult =  resultFormat.format(biWeeklyPayment);
                showResultModify.setText(paymentResult);
            } else if (paymentMonthlyModify.isChecked()) {
                double monthlyPayment = calculateLoanPayment(amountforCal, rateforCal, termforCal, 12);
                paymentCycle = "Monthly";
                paymentResult =  resultFormat.format(monthlyPayment);
                showResultModify.setText(paymentResult);
            }  else{
                Toast.makeText(ModifyActivity.this,"Please select a payment cycle",Toast.LENGTH_SHORT).show();
            }
        }
    }
}