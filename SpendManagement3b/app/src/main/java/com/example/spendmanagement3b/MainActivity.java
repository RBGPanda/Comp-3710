package com.example.spendmanagement3b;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import java.text.DecimalFormat;
import android.database.Cursor;
import android.app.DatePickerDialog;
import java.util.Calendar;
import android.widget.DatePicker;

public class MainActivity extends AppCompatActivity {

    SpendDB db;
    private static DecimalFormat df = new DecimalFormat("0.00");
    Button add, sub, dateButton, startDate, endDate, search;
    EditText amount, desc, startAmount, endAmount;
    TextView bal;
    LinearLayout history;
    DatePickerDialog datePickerDialog;
    int year, month, dayOfMonth;
    Calendar c;
    float rename, lowAmount, highAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = Calendar.getInstance();
        search = findViewById(R.id.search);
        search.setOnClickListener(onClickForSearch());
        startAmount = findViewById(R.id.startAmount);
        endAmount = findViewById(R.id.endAmount);
        endDate = findViewById(R.id.endDate);
        endDate.setOnClickListener(onClickForEndDate());
        dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(ClickForDate());
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        add = findViewById(R.id.add);
        add.setOnClickListener(clickAdd());
        sub = findViewById(R.id.sub);
        sub.setOnClickListener(clickSub());
        amount = findViewById(R.id.amount);
        desc = findViewById(R.id.desc);
        bal = findViewById(R.id.bal);
        history = findViewById(R.id.history);
        startDate = findViewById(R.id.startDate);
        startDate.setOnClickListener(onClickForStartDate());
        rename = 0.0f;

        db = new SpendDB(this);

        Cursor saved = db.getData();
        while(saved.moveToNext()) {
            history.addView(createNewTextView(saved.getString(1)));
            rename = saved.getFloat(2);
        }

        bal.setText("Current Balance: $" + df.format(rename));

    }

    private  OnClickListener onClickForSearch() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                String tester = startDate.getText().toString();
                String tester2 = endDate.getText().toString();
                String firstAmount = startAmount.getText().toString();
                String secondAmount = endAmount.getText().toString();
                history.removeAllViews();
                Cursor DateData = db.getDateData(startDate.getText().toString(), endDate.getText().toString());
                Cursor AmountDateData = db.getDateAndAmountData(startDate.getText().toString(), endDate.getText().toString(), firstAmount, secondAmount);
                Cursor AmountData = db.getAmountData(firstAmount, secondAmount);

                if (!firstAmount.matches("") && !secondAmount.matches("") && !tester.matches("Start Date") && !tester2.matches("End Date")) {
                    while(AmountDateData.moveToNext()) {
                        history.addView(createNewTextView(AmountDateData.getString(1)));
                    }
                }
                else if (!firstAmount.matches("") && !secondAmount.matches("")) {
                    while(AmountData.moveToNext()) {
                        history.addView(createNewTextView(AmountData.getString(1)));
                    }
                }
                else if (!tester.matches("Start Date") && !tester2.matches("End Date")) {
                    while(DateData.moveToNext()) {
                        history.addView(createNewTextView(DateData.getString(1)));
                    }
                }

            }
        };
    }

    private OnClickListener clickAdd() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                history.removeAllViews();

                Cursor saved = db.getData();
                while(saved.moveToNext()) {
                    history.addView(createNewTextView(saved.getString(1)));
                }

                String a = "Adding $" + amount.getText().toString() + " on " + dateButton.getText().toString() +
                        " from " + desc.getText().toString();
                history.addView(createNewTextView(a));

                rename += Float.parseFloat(amount.getText().toString());

                db.addData(a, rename, dateButton.getText().toString(), Float.parseFloat(amount.getText().toString()));
                bal.setText("Current Balance: $" + df.format(rename));
            }
        };
    }

    private OnClickListener clickSub() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                history.removeAllViews();

                Cursor saved = db.getData();
                while(saved.moveToNext()) {
                    history.addView(createNewTextView(saved.getString(1)));
                }

                String a = "Spent $" + amount.getText().toString() + " on " + dateButton.getText().toString() +
                        " for " + desc.getText().toString();
                history.addView(createNewTextView(a));

                rename -= Float.parseFloat(amount.getText().toString());

                db.addData(a, rename, dateButton.getText().toString(), Float.parseFloat(amount.getText().toString()));
                bal.setText("Current Balance: $" + df.format(rename));
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

    private  OnClickListener ClickForDate() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateButton.setText(year + "-" + (month + 1) + "-" + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        };
    }

    private  OnClickListener onClickForStartDate() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                startDate.setText(year + "-" + (month + 1) + "-" + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        };
    }

    private  OnClickListener onClickForEndDate() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                endDate.setText(year + "-" + (month + 1) + "-" + day);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        };
    }

}
