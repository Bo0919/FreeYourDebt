package com.example.freeyourdebt;
import android.Manifest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class MainScreenActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView showUserImg;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);;
    Button btnAddNew,btnManagement,btnList,btnLogout,btnTakePhoto;
    TextView txtGreeting;

    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        databaseHelper = new DatabaseHelper(this);
        showUserImg =findViewById(R.id.imgUser);
        txtGreeting = findViewById(R.id.txtGreeting);
        btnAddNew = findViewById(R.id.btnAddNew);
        btnManagement = findViewById(R.id.btnManagement);
        btnList = findViewById(R.id.btnList);
        btnLogout = findViewById(R.id.btnLogout);
        btnTakePhoto =findViewById(R.id.btnTakePhoto);

        userName ="test";
        txtGreeting.setText("Hello " +userName);


        Cursor cursor = databaseHelper.viewUserImg(userName);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){

                byte[] imgGet = cursor.getBlob(1);
                //ByteArrayInputStream imgGetInput = new ByteArrayInputStream(imgGet);

                Bitmap imageBitmapExtra=BitmapFactory.decodeByteArray(imgGet, 0,imgGet.length);

                //imageBitmap = BitmapFactory.decodeStream(imgGetInput);
                showUserImg.setImageBitmap(imageBitmapExtra);


            }
        }else{}


        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage(v);

            }
        });
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        btnManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this,ManagementActivity.class);
                startActivity(intent);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void captureImage(View view){
        if (ContextCompat.checkSelfPermission(MainScreenActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainScreenActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            showUserImg.setImageBitmap(imageBitmap);

            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imagedata=baos.toByteArray();
            Cursor cursor = databaseHelper.viewUserImg(userName);
            if(cursor.getCount()>0){
                databaseHelper.updateUserImg(userName,imagedata);
                try {
                    baos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }else {
                databaseHelper.addUserImg(userName, imagedata);
                try {
                    baos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}