package com.example.spendmanagement3b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class SpendDB extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "HistoryTable";
    public static final String column1 = "string";
    public static final String column2 = "balance";
    public static final String column3 = "date";
    public static final String column4 = "amount";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                column1 + " TEXT, " + column2 + " FLOAT, " + column3 + " DATE, " + column4 + " FLOAT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public SpendDB(Context context) {
        super(context, TABLE_NAME, null, 1);
    }



    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDateData(String startDate, String endDate)    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + column3 + " >= " + "'" + startDate + "'" + " AND " + column3 + " <= " + "'" + endDate + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAmountData(String startAmount, String endAmount)    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + column4 + " >= " + "'" + startAmount + "'" + " AND " + column4 + " <= " + "'" + endAmount + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDateAndAmountData(String startDate, String endDate, String startAmount, String endAmount)    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + column3 + " >= " + "'" + startDate + "'" + " AND " + column3 + " <= " + "'" + endDate + "'" + " AND " + column4 + " >= " + "'" + startAmount + "'"
            + " AND " + column4 + " <= " + "'" + endAmount + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addData(String a, float balance, String date, float amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column1, a);
        contentValues.put(column2, balance);
        contentValues.put(column3, date);
        contentValues.put(column4, amount);
        db.insert(TABLE_NAME, null, contentValues);
    }
}
