package es.ucm.fdi.animalcare.feature.pets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.feature.login.LoginActivity;

public class PetsActivity extends BaseActivity implements PetsView{
    private PetsPresenter mPetsPresenter;
    private ListView mPetList;
    private Button mAddPet;
    private Button mNewPet;
    private EditText mNamePet;
    private Spinner mTypePet;
    private String mMascotas [] = {"Misifu", "Jack"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        mPetsPresenter = new PetsPresenter(this);

        mPetList = findViewById(R.id.PetsList);
        mAddPet = findViewById(R.id.AddPet);

        //Se debería coger los animales de la base de datos
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, R.layout.pet_list_item, R.id.NamePetView, mMascotas);
        mPetList.setAdapter(adapter);

        mAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPetsPresenter.addNewPet();
            }
        });

    }

    public void addNewPet() {
        setContentView(R.layout.activity_pets_new);

        mPetsPresenter = new PetsPresenter(this);

        mNamePet = findViewById(R.id.editText_newpet_name);
        mTypePet = findViewById(R.id.spinner);

        String [] options = {"Perro","Gato","Pajaro","Pez","Caballo"};

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
        setContentView(R.layout.activity_pets);
        Toast toast = Toast.makeText(this, "Animal Guardado", Toast.LENGTH_LONG);
        toast.show();
    }
}