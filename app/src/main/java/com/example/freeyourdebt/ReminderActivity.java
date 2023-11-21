package com.example.freeyourdebt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReminderActivity extends AppCompatActivity {
    TextView debtorNameTextView;
    TextView amountTextView;
    TextView dueDateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        debtorNameTextView = findViewById(R.id.debtorNameTextView);
        amountTextView = findViewById(R.id.amountTextView);
        dueDateTextView = findViewById(R.id.dueDateTextView);

        // Retrieve data from the intent
        long debtId = getIntent().getLongExtra("debtId", -1);
        String debtorName = getIntent().getStringExtra("debtorName");
        double amount = getIntent().getDoubleExtra("amount", 0.0);
        long dueDateMillis = getIntent().getLongExtra("dueDate", 0);

        // Format the date for display
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDueDate = dateFormat.format(new Date(dueDateMillis));

        // Set data in TextViews
        debtorNameTextView.setText("Debtor: " + debtorName);
        amountTextView.setText("Amount: $" + amount);
        dueDateTextView.setText("Due Date: " + formattedDueDate);
    }

}