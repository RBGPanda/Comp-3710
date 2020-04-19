package com.example.spendmanagement3b;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.content.SharedPreferences;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.TimePickerDialog;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    TextView bal;
    Button add, sub, dateButton, startDate, endDate;
    EditText amount, desc, startAmount, endAmount;
    LinearLayout history;
    SharedPreferences sharedPreferences;
    private static DecimalFormat decimalformat = new DecimalFormat("0.00");
    SpendDB db;
    float rename;
    int year, month, day, date;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        c = Calendar.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amount = findViewById(R.id.amount);
        desc = findViewById(R.id.desc);
        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        bal = findViewById(R.id.bal);
        history = findViewById(R.id.history);
        add.setOnClickListener(clickAdd());
        sub.setOnClickListener(clickSub());
        db = new SpendDB(this);
        rename = 0.0f;
        dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(ClickForDate());
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        startDate.setOnClickListener(ClickForStartDate());
        endDate.setOnClickListener(ClickForEndDate());


        Cursor saved = db.getData();
        while (saved.moveToNext()) {
            history.addView(createNewTextView(saved.getString(1)));
            rename = saved.getFloat(2);
        }

        bal.setText("Current Balance: $" + decimalformat.format(rename));
    }

    private OnClickListener clickAdd() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "Adding $" + amount.getText().toString() + " on " + dateButton.getText().toString() + " from " + desc.getText().toString();
                history.addView(createNewTextView(a));
                rename += Float.parseFloat(amount.getText().toString());
                db.addData(a, rename, dateButton.getText().toString());
                bal.setText("Current Balance: $" + decimalformat.format(rename));

            }
        };
    }

    private OnClickListener clickSub() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "Spent $" + amount.getText().toString() + " on " + dateButton.getText().toString() + " from " + desc.getText().toString();
                history.addView(createNewTextView(a));
                rename -= Float.parseFloat(amount.getText().toString());
                db.addData(a, rename, dateButton.getText().toString());
                bal.setText("Current Balance: $" + decimalformat.format(rename));
            }
        };
    }

    private TextView createNewTextView(String text) {
        final LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final TextView newNote = new TextView(this);
        newNote.setLayoutParams(lparams);
        newNote.setText(text);
        newNote.setTextSize(15);
        newNote.setPadding(20, 50, 20, 50);
        return newNote;
    }


    private OnClickListener ClickForDate() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                date = c.get(Calendar.DATE);
                //tried to use date did not work.
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateButton.setText((year) + "-" + (month + 1) + "-" + day);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        };
    }

    private  OnClickListener ClickForStartDate() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                startDate.setText((month+1) + "/" + day + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(0);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        };
    }

    private  OnClickListener ClickForEndDate() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                endDate.setText((month+1) + "/" + day + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(0);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        };
    }


}
