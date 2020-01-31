package com.example.temperatureconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtF;
    TextView txtC;
    Button btnConvertF2C;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtF = findViewById(R.id.txtF);
        txtC = findViewById(R.id.txtC);
        btnConvertF2C = findViewById(R.id.btnConvertF2C);

        btnConvertF2C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strF = txtF.getText().toString();
                Log.i("Info", "String of txtF = " + strF);

                if (strF.length() == 0) {
                    Toast.makeText(v.getContext(),
                            "ERROR: Input value for F degree is empty",
                            Toast.LENGTH_LONG).show();
                    Log.e("Error", "String of txtF is empty!");
                    return;
                }

                double valF = 32.0;
                try {
                    valF = Double.parseDouble(strF);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e("Error", "String of txtF is not parsable");
                    Toast.makeText(v.getContext(),
                            "ERROR: Input value for F degree is invalid",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                double valC = (valF - 32)*5/9;

                String strC = Double.toString(valC);

                Log.i("Info", "String of txtC = " + strC);

                txtC.setText(strC);
            }
        });

    }
}
