package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class AddActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Button btnCal,btnAdd2Record,btnReturnAdd;
    Spinner debtTypeInput;
    RadioButton paymentWeekly,paymentMonthly,paymentBiweekly;
    EditText debtNameInput,debtAmountInput,debtRateInput,debtTermsInput,debtMemoInput;
    TextView showResult;
    String debtName,debtAmount,debtRate,debtTerms,debtMemo,debtType,paymentCycle,paymentResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        debtNameInput = findViewById(R.id.txtDebtNameInput);
        debtTypeInput = findViewById(R.id.spinnerDebtType);
        debtAmountInput = findViewById(R.id.txtAmountInput);
        debtRateInput = findViewById(R.id.txtInterestInput);
        debtTermsInput = findViewById(R.id.txtTermInput);
        debtMemoInput = findViewById(R.id.txtDebtMemo);

        paymentWeekly = findViewById(R.id.rbWeekly);
        paymentMonthly = findViewById(R.id.rbMonthly);
        paymentBiweekly = findViewById(R.id.rbBWeekly);

        showResult = findViewById(R.id.txtShowPayment);

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

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calPayment();
            }
        });

        btnAdd2Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add2DB();
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
        debtName = debtNameInput.getText().toString();
        debtAmount = debtAmountInput.getText().toString();
        debtRate = debtRateInput.getText().toString();
        debtTerms = debtTermsInput.getText().toString();
        debtMemo = debtMemoInput.getText().toString();
        debtType = debtTypeInput.getSelectedItem().toString();
        paymentResult = "";
        if(debtName.length()==0){
            Toast.makeText(AddActivity.this,"Debt Name can not be empty",Toast.LENGTH_SHORT).show();
        } else if (debtAmount.length()==0){
            Toast.makeText(AddActivity.this,"Debt Amount can not be empty",Toast.LENGTH_SHORT).show();
        } else if (debtRate.length()==0){
            Toast.makeText(AddActivity.this,"Debt Rate can not be empty",Toast.LENGTH_SHORT).show();
        } else if (debtTerms.length()==0){
            Toast.makeText(AddActivity.this,"Debt Terms can not be empty",Toast.LENGTH_SHORT).show();
        } else {
            DecimalFormat resultFormat = new DecimalFormat("0.###");
            int termforCal = Integer.parseInt(debtTerms);
            Double amountforCal = Double.parseDouble(debtAmount);
            Double rateforCal = Double.parseDouble(debtRate);
            if (paymentWeekly.isChecked()) {
                double weeklyPayment = calculateLoanPayment(amountforCal, rateforCal, termforCal, 52);
                paymentCycle = "Weekly";
                paymentResult =  resultFormat.format(weeklyPayment);
                showResult.setText(paymentResult);
            } else if (paymentBiweekly.isChecked()) {
                double biWeeklyPayment = calculateLoanPayment(amountforCal, rateforCal, termforCal, 26);
                paymentCycle = "Bi-weekly";
                paymentResult =  resultFormat.format(biWeeklyPayment);
                showResult.setText(paymentResult);
            } else if (paymentMonthly.isChecked()) {
                double monthlyPayment = calculateLoanPayment(amountforCal, rateforCal, termforCal, 12);
                paymentCycle = "Monthly";
                paymentResult =  resultFormat.format(monthlyPayment);
                showResult.setText(paymentResult);
            }  else{
                Toast.makeText(AddActivity.this,"Please select a payment cycle",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void add2DB(){

        if (showResult.getText().length() == 0) {
            Toast.makeText(AddActivity.this,"Payment can not be empty. Do calculating",Toast.LENGTH_SHORT).show();
            calPayment();
            } else{
            databaseHelper = new DatabaseHelper(this);
            boolean isAdded = databaseHelper.addDebtData("test",debtName,debtType,debtAmount,debtRate,debtTerms,debtMemo,paymentCycle,showResult.getText().toString());
            if(isAdded){
                Toast.makeText(AddActivity.this,"added data successfully",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AddActivity.this,"failed to add data",Toast.LENGTH_SHORT).show();
            }
        }
    }
}