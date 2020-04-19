package com.example.spendmanagement3b;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;



//https://developer.android.com/training/data-storage/sqlite
//https://developer.android.com/reference/android/database/sqlite/package-summary
//https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase
//https://www.tutorialspoint.com/android/android_sqlite_database.htm

public class SpendDB extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "HistoryTable3";
    public static final String column1 = "description";
    public static final String column2 = "amount";
    public static final String column3 = "date";

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                column1 + " TEXT, " + column2 + " FLOAT, " + column3 + " DATE)";
        sqldb.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int oldVersion, int newVersion) {
        sqldb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqldb);
    }

    public SpendDB(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    public Cursor getData() {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = sqldb.rawQuery(query, null);
        return data;
    }

    public void addData(String a, float bal, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column1, a);
        contentValues.put(column2, bal);
        contentValues.put(column3, date);
        db.insert(TABLE_NAME, null, contentValues);
    }
}
