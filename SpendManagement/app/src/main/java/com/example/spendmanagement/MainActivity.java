package com.example.spendmanagement;

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


public class MainActivity extends AppCompatActivity {
    TextView bal;
    Button add, sub;
    EditText date, amount, desc;
    LinearLayout log;
    SharedPreferences sharedPreferences;
    public static final String spendManagement = "SpendingManagement";
    public static final String savedBal = "SavedBalance";
    public static final String savedLog = "savedLog";
    private static DecimalFormat decimalformat = new DecimalFormat("0.00");
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
        log = findViewById(R.id.log);

        sharedPreferences = getSharedPreferences(spendManagement, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.contains(savedBal)) {
            bal.setText("Current Balance: $" + decimalformat.format(sharedPreferences.getFloat(savedBal, 0.0f)));
        }
        else
        {
            bal.setText("Current Balance: $0.0");
            editor.putFloat(savedBal, 0.0f);
        }

        if(sharedPreferences.contains(savedLog))    {
            for (int i = 0; i <= sharedPreferences.getInt(savedLog, 0); i++)    {
                log.addView(createNewTextView(sharedPreferences.getString(Integer.toString(i), "")));
            }
        }
        else    {
            editor.putInt(savedLog, -1);
        }

        add.setOnClickListener(clickAdd());
        sub.setOnClickListener(clickSub());
    }
    private OnClickListener clickAdd()  {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "Adding $" + amount.getText().toString() + " on " + date.getText().toString() + " from " + desc.getText().toString();
                log.addView(createNewTextView(a));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                float currentBal = sharedPreferences.getFloat(savedBal, 0.0f) + Float.parseFloat(amount.getText().toString());
                int saved = sharedPreferences.getInt(savedLog, -1);
                saved += 1;
                editor.putFloat(savedBal, currentBal);
                editor.putInt(savedLog, saved);
                editor.putString(Integer.toString(saved), a);
                editor.commit();
                bal.setText("Current Balance: $" + decimalformat.format(sharedPreferences.getFloat(savedBal, 0.0f)));
            }
        };
    }

    private OnClickListener clickSub()  {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "Adding $" + amount.getText().toString() + " on " + date.getText().toString() + " from " + desc.getText().toString();
                log.addView(createNewTextView(a));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                float currentBal = sharedPreferences.getFloat(savedBal, 0.0f) - Float.parseFloat(amount.getText().toString());
                int saved = sharedPreferences.getInt(savedLog, -1);
                saved += 1;
                editor.putFloat(savedBal, currentBal);
                editor.putInt(savedLog, saved);
                editor.putString(Integer.toString(saved), a);
                editor.commit();
                bal.setText("Current Balance: $" + decimalformat.format(sharedPreferences.getFloat(savedBal, 0.0f)));
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
