package es.fdi.ucm.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CalculatorResultActivity extends AppCompatActivity {

    private static final String TAG = CalculatorResultActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_result);

        Intent intent = getIntent();
        int sum = intent.getIntExtra("sum", 0);
        TextView tv = findViewById(R.id.textView);

        Log.d(TAG, "Showing addition result.");
        tv.setText(String.valueOf(sum));
    }
}