package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorResultActivity extends AppCompatActivity {

    private TextView text;
    private Integer suma;
    private Bundle result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);
        Log.i("CalculatorResultActivity", "Cambio de ventana");
        result = getIntent().getExtras();
        suma = result.getInt("result");
        text = (TextView) findViewById(R.id.textView);
        text.setText(suma.toString());
    }
}