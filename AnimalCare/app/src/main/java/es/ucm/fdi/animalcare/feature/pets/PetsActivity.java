package es.ucm.fdi.animalcare.feature.pets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;

public class PetsActivity extends BaseActivity implements PetsView{
    private PetsPresenter mPetsPresenter;
    private Button mNewPet;
    private EditText mNamePet;
    private Spinner mTypePet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        mPetsPresenter = new PetsPresenter(this);

        mNamePet = findViewById(R.id.editText_newpet_name);
        mTypePet = findViewById(R.id.spinner);

        String [] options = {"Perro","Gato","Pajaro","Pez"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        mTypePet.setAdapter(adapter);

        mNewPet = findViewById(R.id.button_newpet_add);
        mNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mPetsPresenter.validateNewPet(String.valueOf(mNamePet.getText()), mTypePet.getSelectedItem().toString()) ;
            }
        });
    }

    @Override
    public boolean fillField() {
        Toast toast = Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_LONG);
        toast.show();
        return false;
    }

    @Override
    public void NewPetSuccessful() {
        //Enviar a la pagina de Pets
    }
}