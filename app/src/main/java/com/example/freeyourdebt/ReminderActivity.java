package com.example.freeyourdebt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReminderActivity extends AppCompatActivity {

    TextView debtorNameTextView;
    TextView paymentTypeTextView;
    TextView debtCountTextView;
    TextView paymentTextView;
    TextView dueDateTextView;

    // Declare formattedDueDate as a class-level variable
    private String formattedDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        debtorNameTextView = findViewById(R.id.debtorNameTextView);
        paymentTypeTextView = findViewById(R.id.paymentTypeTextView);
        debtCountTextView = findViewById(R.id.debtCountTextView);
        paymentTextView = findViewById(R.id.paymentTextView);
        dueDateTextView = findViewById(R.id.dueDateTextView);

        Button backButton = findViewById(R.id.backButton);
        Button setReminderButton = findViewById(R.id.setReminderButton);

        Intent intent = getIntent();
        if (intent != null) {

            long debtId = getIntent().getLongExtra("debtId", -1);
            String debtorName = getIntent().getStringExtra("debtorName");
            String paymentType = getIntent().getStringExtra("paymentType");
            int debtCount = getIntent().getIntExtra("debtCount", 0);
            double payment = getIntent().getDoubleExtra("payment", 0.0);


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            formattedDueDate = dateFormat.format(new Date(getNextPaymentDate(debtCount)));

            // Set data in TextViews
            debtorNameTextView.setText("Debt Name: " + (debtorName != null ? debtorName : "N/A"));
            paymentTypeTextView.setText("Payment Type: " + (paymentType != null ? paymentType : "N/A"));
            debtCountTextView.setText("Debt Count: " + debtCount);
            paymentTextView.setText("Payment: $" + payment);
            dueDateTextView.setText("Next Payment Due Date: " + formattedDueDate);
        }


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ReminderActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });


        setReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showToast("Next Payment Date: " + formattedDueDate);
            }
        });
    }


    private long getNextPaymentDate(int debtCount) {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, debtCount);
        return calendar.getTimeInMillis();
    }


    private void showToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

