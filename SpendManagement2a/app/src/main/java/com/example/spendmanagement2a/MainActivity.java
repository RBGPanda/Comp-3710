package com.example.spendmanagement2a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.content.SharedPreferences;
import java.text.DecimalFormat;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    TextView bal;
    Button add, sub;
    EditText date, amount, desc;
    LinearLayout history;
    SharedPreferences sharedPreferences;
    private static DecimalFormat decimalformat = new DecimalFormat("0.00");
    SpendDB db;
    float rename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = findViewById(R.id.date);
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
        Cursor saved = db.getData();
        while(saved.moveToNext()) {
            history.addView(createNewTextView(saved.getString(1)));
            rename = saved.getFloat(2);
        }

        bal.setText("Current Balance: $" + decimalformat.format(rename));
    }
    private OnClickListener clickAdd()  {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "Adding $" + amount.getText().toString() + " on " + date.getText().toString() + " from " + desc.getText().toString();
                history.addView(createNewTextView(a));
                rename += Float.parseFloat(amount.getText().toString());
                db.addData(a, rename);
                bal.setText("Current Balance: $" + decimalformat.format(rename));
            }
        };
    }

    private OnClickListener clickSub()  {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "Spent $" + amount.getText().toString() + " on " + date.getText().toString() + " from " + desc.getText().toString();
                history.addView(createNewTextView(a));
                rename -= Float.parseFloat(amount.getText().toString());
                db.addData(a, rename);
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
}