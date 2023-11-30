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
import java.util.TimeZone;

public class ReminderActivity extends AppCompatActivity {

    TextView debtorNameTextView;
    TextView paymentTypeTextView;
    TextView debtCountTextView;
    TextView paymentTextView;
    TextView dueDateTextView;

    // Declare formattedDueDate as a class-level variable
    private String formattedDueDate,debtId,debtorName,paymentType,payment,debtAmount;

    String rruleSeting = null; // ass a string for setting schedule paycycle
    long dateForAdjustment; // add a long for if condition for different payment cycle
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC")); // add Calendar class for using calendar
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
            //make all variable to the top class
            //long debtId = getIntent().getLongExtra("debtId", -1); due to the data was save as string, set as String
             debtId = getIntent().getStringExtra("debtId");
             debtorName = getIntent().getStringExtra("debtorName");
             paymentType = getIntent().getStringExtra("debtPayCycle");//corrected Intent id from paymentType to debtPayCycle
            int debtCount = getIntent().getIntExtra("debtCount", 0);
            //double payment = getIntent().getDoubleExtra("payment", 0.0);due to the data was save as string, set as String
             payment = getIntent().getStringExtra("payAmount"); //corrected Intent id from payment to payAmount
            //added debt Amount variable
             debtAmount = getIntent().getStringExtra("amount");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            formattedDueDate = dateFormat.format(new Date(getNextPaymentDate(debtCount)));

            // Set data in TextViews
            debtorNameTextView.setText("Debt Name: " + (debtorName != null ? debtorName : debtorName)); //changed N/A to the variable
            paymentTypeTextView.setText("Payment Type: " + (paymentType != null ? paymentType : paymentType));//changed N/A to the variable
            debtCountTextView.setText("Debt Amount: $" + debtAmount); //I do not what is the debt count. So, I changed it to debt amount
            paymentTextView.setText("Payment: $" + payment);

            //set if condition for payment Type to show different next payment due
            if(paymentType.equals("Weekly")) {
                dateForAdjustment = cal.getTimeInMillis()+86400000*7;
                formattedDueDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateForAdjustment);
                rruleSeting ="FREQ=WEEKLY";
            } else if (paymentType.equals("Bi-weekly")) {
                dateForAdjustment = cal.getTimeInMillis()+86400000*14;
                formattedDueDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateForAdjustment);
                rruleSeting ="FREQ=DAILY;INTERVAL=14";
            }else {
                cal.add(Calendar.MONTH, +1);
                dateForAdjustment = cal.getTimeInMillis();
                formattedDueDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateForAdjustment);
                rruleSeting ="FREQ=MONTHLY";
            }
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

                //showToast("Next Payment Date: " + formattedDueDate); call the schedule app instead
                setCalendar();
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

    public void setCalendar() {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", rruleSeting);
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("description", "This reminder is for debt ID: "+ debtId);
        intent.putExtra("title", debtorName+", Payment: "+payment);
        startActivity(intent);
    }
}

