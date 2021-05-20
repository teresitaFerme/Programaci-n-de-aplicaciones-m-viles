package es.ucm.fdi.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Calculator calculator = new Calculator();
    EditText editTextX;
    EditText editTextY;
    public static final String EXTRA_MESSAGE_KEY =
            "com.example.android.twoactivities.extra.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "Inicio ventana");
    }

    public void addXandY(View v){
        Log.i("MainActivity", "Boton pulsado.");

        editTextX = (EditText)findViewById(R.id.editTextNumberDecimal);
        editTextY = (EditText)findViewById(R.id.editTextNumberDecimal3);
        Integer x = Integer.parseInt(editTextX.getText().toString());
        Integer y = Integer.parseInt(editTextY.getText().toString());

        Integer sum = calculator.add(x, y);

        Intent intent = new Intent(this, CalculatorResultActivity.class);
        intent.putExtra("result", sum);
        startActivity(intent);

    }

}