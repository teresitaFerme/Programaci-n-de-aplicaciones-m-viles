package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Calculator calculator = new Calculator();
    private EditText editTextX, editTextY;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextX = findViewById(R.id.editTextX);
        editTextY = findViewById(R.id.editTextY);
        Log.d(TAG, "View creado");
    }

    public void addXandY(View view){
        Log.d(TAG, "Calculando la suma");
        Integer result = calculator.add(Integer.parseInt(editTextX.getText().toString()), Integer.parseInt(editTextY.getText().toString()));
        Log.d(TAG, "Suma calculado: " + result.toString());
        Intent intent = new Intent(this, CalculatorResultActivity.class);
        intent.putExtra("result",result);
        Log.d(TAG, "Iniciando CalculatorResultActivity");
        startActivity(intent);
    }
}