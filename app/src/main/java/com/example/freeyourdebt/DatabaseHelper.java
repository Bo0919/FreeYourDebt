package com.example.freeyourdebt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseHelper extends SQLiteOpenHelper {
    String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    final static String DATABASE_NAME = "Information.db";
    final static int DATABASE_VERSION = 1;

    //table1
    final static String TABLE1_NAME = "USER_INFO";
    final static String T1COL1 = "user_name";
    final static String T1COL2 = "password";
    final static String T1COL3 = "create_date";

    //table2
    final static String TABLE2_NAME = "DEBT_LIST ";
    final static String T2COL1 = "bid";
    final static String T2COL2 = "user_name";
    final static String T2COL3 = "debt_name";
    final static String T2COL4 = "debt_type";
    final static String T2COL5 = "debt_amount";
    final static String T2COL6 = "debt_rate";
    final static String T2COL7 = "debt_terms";
    final static String T2COL8 = "debt_memo";
    final static String T2COL9 = "payment_cycle";
    final static String T2COL10 = "payment_amount";
    final static String T2COL11 = "create_date";
    final static String T2COL12 = "modify_date";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE1_NAME + "( " + T1COL1 + " TEXT, " + T1COL2 + " TEXT, " + T1COL3 + " TEXT) ";

        String query2 = "CREATE TABLE " + TABLE2_NAME + "( " + T2COL1 + " INTEGER PRIMARY KEY, " + T2COL2 + " TEXT, " + T2COL3 + " TEXT, " + T2COL4 + " TEXT, " + T2COL5 + " TEXT," + T2COL6 + " TEXT, " + T2COL7 + " TEXT, " + T2COL8 + " TEXT, " + T2COL9 + " TEXT," + T2COL10 + " TEXT, " + T2COL11 + " TEXT, " + T2COL12 + " TEXT) ";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(sqLiteDatabase);
    }
    public Cursor login(String username,String password){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME;;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor;
    }
    public Cursor viewDebtList() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE2_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor;
    }

    public boolean addUserData(String username,String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        if (!sqLiteDatabase.isReadOnly()) {
            // Enable foreign key constraints
            sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
        }
        ContentValues values = new ContentValues();
        values.put(T1COL1,username);
        values.put(T1COL2,password);
        values.put(T1COL3,currentDateandTime);

        long l = sqLiteDatabase.insert(TABLE1_NAME,null,values);

        if(l>0)
            return true;
        else
            return false;
    }

    public boolean addDebtData(String username,String debtname,String debttype, String debtamount,String debtrate,String debtterms,
                           String debtmemo,String paymentcycle,String paymentamount, String creatdata){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        if (!sqLiteDatabase.isReadOnly()) {
            // Enable foreign key constraints
            sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
        }
        ContentValues values = new ContentValues();
        values.put(T2COL2,username);
        values.put(T2COL3,debtname);
        values.put(T2COL4,debttype);
        values.put(T2COL5,debtamount);
        values.put(T2COL6,debtrate);
        values.put(T2COL7,debtterms);
        values.put(T2COL8,debtmemo);
        values.put(T2COL9,paymentcycle);
        values.put(T2COL10,paymentamount);
        values.put(T2COL11,creatdata);

        long l = sqLiteDatabase.insert(TABLE1_NAME,null,values);

        if(l>0)
            return true;
        else
            return false;
    }
    public boolean updateDebtRec(String bid,String debtname,String debttype, String debtamount,String debtrate,String debtterms,
                                 String debtmemo,String paymentcycle,String paymentamount, String modifydate){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        if (!sqLiteDatabase.isReadOnly()) {
            // Enable foreign key constraints
            sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
        }
        ContentValues values = new ContentValues();
        values.put(T2COL3,debtname);
        values.put(T2COL4,debttype);
        values.put(T2COL5,debtamount);
        values.put(T2COL6,debtrate);
        values.put(T2COL7,debtterms);
        values.put(T2COL8,debtmemo);
        values.put(T2COL9,paymentcycle);
        values.put(T2COL10,paymentamount);
        values.put(T2COL11,modifydate);
        int i = sqLiteDatabase.update(TABLE2_NAME,
                values,"bid=?",new String[]{bid});
        if(i>0)
            return true;
        else
            return false;
    }
    public boolean deleteDebtRec(String bid){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        if (!sqLiteDatabase.isReadOnly()) {
            // Enable foreign key constraints
            sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
        }
        int i = sqLiteDatabase.delete(TABLE2_NAME,"bid=?",
                new String[]{bid});
        if(i>0)
            return true;
        else
            return false;
    }
}

