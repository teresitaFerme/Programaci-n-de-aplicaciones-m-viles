package es.ucm.fdi.animalcare.feature.pets.newPets;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;

public class NewPetsActivity extends ToolBarManagement implements NewPetsView {
    private NewPetsPresenter mNewPetsPresenter;
    private EditText mNamePet;
    private Spinner mTypePet;
    private Button mNewPet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_new);
        setUpToolbar();
        bindViews();

        mNewPetsPresenter = new NewPetsPresenter(this);

        Resources res = App.getApp().getResources();
        String[] options = res.getStringArray(R.array.Pets_animals);
        mNamePet.setHint(res.getString(R.string.nombre_mascota));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        mTypePet.setAdapter(adapter);
        mNewPet.setText(res.getString(R.string.aceptar));

        mNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type;
                switch(mTypePet.getSelectedItem().toString()){
                    case "Perro":
                    case "Dog":
                        type = "Perro";

                        break;
                    case "Gato":
                    case "Cat":
                        type = "Gato";

                        break;
                    case "Pajaro":
                    case "Bird":
                        type = "Pajaro";

                        break;
                    case "Caballo":
                    case "Horse":
                        type = "Caballo";

                        break;
                    case "Tortuga":
                    case "Turtle":
                        type = "Tortuga";

                        break;
                    default:
                        type = "Pez";
                        break;
                }
                mNewPetsPresenter.validateNewPet(String.valueOf(mNamePet.getText()), type, App.getApp().getUser().getmId()) ;
            }
        });

    }

    @Override
    public void NewPetSuccessful() {
        Toast toast = Toast.makeText(this, R.string.save_pet, Toast.LENGTH_LONG);
        toast.show();
        returnPets();
    }

    @Override
    public void fillField() {
        Toast toast = Toast.makeText(this, R.string.fill_please, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void NewPetError() {
        Toast toast = Toast.makeText(this, R.string.error_save_pet, Toast.LENGTH_LONG);
        toast.show();
        returnPets();
    }

    public void returnPets(){
        Intent intent = new Intent(this, PetsActivity.class);
        startActivity(intent);
    }

    @Override
    public void bindViews() {
        mNamePet = findViewById(R.id.editText_newpet_name);
        mTypePet = findViewById(R.id.spinner);
        mNewPet = findViewById(R.id.button_newpet_add);
    }

    public void setUpToolbar() {
        super.setUpToolbar(R.id.button_toolbar_pets);
    }
}
