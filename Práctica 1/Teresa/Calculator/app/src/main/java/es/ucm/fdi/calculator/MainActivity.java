package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_LOG = MainActivity.class.getSimpleName();
    private Calculator calculator;
    private EditText editTextX;
    private EditText editTextY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculator = new Calculator();
        editTextX = findViewById(R.id.editTextX);
        editTextY = findViewById(R.id.editTextY);
        Log.d(TAG_LOG, "Main Activity created with no problem");
    }


    public void addXandY(View view) {
        Intent intent = new Intent(this , CalculatorResultActivity.class);
        String string_x = String.valueOf(editTextX.getText());
        String string_y = String.valueOf(editTextY.getText());
        int x = Integer.parseInt(string_x);
        int y = Integer.parseInt(string_y);
        int resultado = calculator.add(x, y);
        Log.d(TAG_LOG, "Main Activity: resultado obtenido");
        intent.putExtra("resultado", resultado);
        startActivity(intent);
        Log.d(TAG_LOG, "Vuelta a MainActivity");
    }
}