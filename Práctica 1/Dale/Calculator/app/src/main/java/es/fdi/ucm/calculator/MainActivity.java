package es.fdi.ucm.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Calculator calculator;
    EditText editTextX;
    EditText editTextY;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculator = new Calculator();
        editTextX = findViewById(R.id.editTextNumberDecimal1);
        editTextY = findViewById(R.id.editTextNumberDecimal2);
        Log.d(TAG, "Waiting for x and y values...");
    }

    public void addXandY(View view){
        Intent intent = new Intent(this, CalculatorResultActivity.class);
        int sum = 0;
        int x, y;

        x = Integer.parseInt(String.valueOf(editTextX.getText()));
        y = Integer.parseInt(String.valueOf(editTextY.getText()));
        sum = calculator.add(x, y);
        intent.putExtra("sum", sum);
        startActivity(intent);
        Log.d(TAG, "Executing add function...");
    }
}