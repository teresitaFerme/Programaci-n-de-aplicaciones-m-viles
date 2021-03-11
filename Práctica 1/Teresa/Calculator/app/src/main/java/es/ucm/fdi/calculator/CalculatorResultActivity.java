package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CalculatorResultActivity extends AppCompatActivity {
    private static final String TAG_LOG = CalculatorResultActivity.class.getSimpleName();

    private TextView show_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);
        Intent intent = getIntent();
        int resultado = intent.getIntExtra("resultado", 0);
        show_result = findViewById(R.id.textViewResult);
        show_result.setText(Integer.toString(resultado));
        Log.d(TAG_LOG, "Result showed");
    }
}