package es.ucm.fdi.animalcare.feature.pets;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.Pets;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class PetsActivity extends BaseActivity implements PetsView, ToolBarManagement {
    private User user;
    private PetsPresenter mPetsPresenter;
    private ImageView mButton;
    private RecyclerView mPetList;
    private Button mAddPet;
    private Button mNewPet;
    private EditText mNamePet;
    private Spinner mTypePet;
    private PetsAdapter petAdapter;
    private List<Pets> listPets;
    private RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.iconColor));

        user = (User) getIntent().getSerializableExtra("user");

        mPetsPresenter = new PetsPresenter(this);

        mPetList = findViewById(R.id.PetsList);
        mAddPet = findViewById(R.id.AddPet);

        updateList(user.getmId());

        mAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPetsPresenter.addNewPet();
            }
        });

    }

    @Override
    public void launchFromToolbar(View view) {
        if(view.getId() != (R.id.button_toolbar_pets)){
            Intent intent;
            switch (view.getId()){
                case R.id.button_toolbar_upcoming:
                    intent = new Intent(this, UpcomingActivity.class);
                    break;
                case R.id.button_toolbar_user:
                    intent = new Intent(this, UserActivity.class);
                    break;
                case R.id.button_toolbar_calendar:
                    intent = new Intent(this, CalendarActivity.class);
                    break;
                default:
                    intent = new Intent(this, SettingsActivity.class);
                    break;

            }
            startActivity(intent);
        }
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
                mPetsPresenter.validateNewPet(String.valueOf(mNamePet.getText()), mTypePet.getSelectedItem().toString(), user.getmId()) ;
            }
        });
    }

    public void  updateList(Integer userId){
        listPets = new ArrayList<>();
        listPets = mPetsPresenter.validateUserPets(userId);

        recyclerView = findViewById(R.id.PetsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        petAdapter = new PetsAdapter( listPets, this);
        recyclerView.setAdapter(petAdapter);
    }

    @Override
    public void noRegister() {
        Toast toast = Toast.makeText(this, "Logeate para mostrar tus mascotas", Toast.LENGTH_LONG);
        toast.show();
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
        updateList(user.getmId());

    }
}