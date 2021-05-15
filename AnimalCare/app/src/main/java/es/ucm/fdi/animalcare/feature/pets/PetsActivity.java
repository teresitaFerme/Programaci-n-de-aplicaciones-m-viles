package es.ucm.fdi.animalcare.feature.pets;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.Pets;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.pets.profilePet.ProfilePetActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.pets.newPets.NewPetsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class PetsActivity extends BaseActivity implements PetsView, ToolBarManagement {
    private User user;
    private PetsPresenter mPetsPresenter;
    private RecyclerView mPetList;
    private Button mEditPet;
    private Button mDeletePet;
    private Button mAddPet;
    private Button mNewPet;
    private EditText mNamePet;
    private TextView mShowName;
    private ImageView mShowImage;
    private Spinner mTypePet;
   private PetsAdapter mPetAdapter;
    private List<Pets> listPets;
    private RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        findViewById(R.id.button_toolbar_pets).getBackground().setTint(App.getApp().getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));

        user = (User) getIntent().getSerializableExtra("user");

        mPetsPresenter = new PetsPresenter(this);

        mPetList = findViewById(R.id.PetsList);
        mAddPet = findViewById(R.id.AddPet);

        updateList();

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
            intent.putExtra("user", user);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void  updateList(){
        listPets = new ArrayList<>();
        listPets = mPetsPresenter.validateUserPets(user.getmId());
        user.setmPetList(listPets);

        recyclerView = findViewById(R.id.PetsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mPetAdapter = new PetsAdapter( listPets, this);

        mPetAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Selecciono: " + listPets.get
                        (recyclerView.getChildAdapterPosition(view)).getName(),Toast.LENGTH_SHORT).show();

                viewPet(listPets.get(recyclerView.getChildAdapterPosition(view)).getName(),listPets.get(recyclerView.getChildAdapterPosition(view)).getType(), listPets.get(recyclerView.getChildAdapterPosition(view)).getId());
            }
        });
        recyclerView.setAdapter(mPetAdapter);

        mAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPetsPresenter.addNewPet();
            }
        });

    }
    
    public void addNewPet() {
        Intent intent = new Intent(this, NewPetsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void editPet(Integer id) {
        setContentView(R.layout.activity_pets_new);

        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.iconColor));

        mPetsPresenter = new PetsPresenter(this);

        mNamePet = findViewById(R.id.editText_newpet_name);
        mTypePet = findViewById(R.id.spinner);

        String [] options = {"Perro","Gato","Pajaro","Pez","Tortuga","Caballo"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        mTypePet.setAdapter(adapter);

        mNewPet = findViewById(R.id.button_newpet_add);
        mNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPetsPresenter.validateEditPet(id, String.valueOf(mNamePet.getText()), mTypePet.getSelectedItem().toString(), user.getmId()) ;
            }
        });
    }

    public void viewPet(String name,String type, Integer id){
        setContentView(R.layout.activity_pets_profile);

        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.iconColor));

        mPetsPresenter = new PetsPresenter(this);

        mShowName = findViewById(R.id.NamePetView);
        mShowImage = findViewById(R.id.imgPet);
        mEditPet = findViewById(R.id.buttonEditPet);
        mDeletePet = findViewById(R.id.buttonDeletePet);

        mShowName.setText(name);
        imagePet(mShowImage,type);

        mEditPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPetsPresenter.editPet(id);
            }
        });

        mDeletePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPetsPresenter.validateDeletePet(id);
            }
        });
    }

    public void imagePet (ImageView image, String type){
        switch (type) {
            case "Perro": image.setImageResource(R.drawable.dog_green); break;
            case "Gato": image.setImageResource(R.drawable.cat_green); break;
            case "Pajaro": image.setImageResource(R.drawable.bird_green); break;
            case "Caballo": image.setImageResource(R.drawable.horse_green); break;
            case "Pez": image.setImageResource(R.drawable.fish_green); break;
            case "Tortuga": image.setImageResource(R.drawable.turtle_green); break;
            default: image.setImageResource(R.drawable.dog_green); break;
        }
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
        Toast toast = Toast.makeText(this, "Animal guardado", Toast.LENGTH_LONG);
        toast.show();
        updateList();
    }

    public void DeletePetSuccessful() {
        setContentView(R.layout.activity_pets);
        Toast toast = Toast.makeText(this, "Animal borrado", Toast.LENGTH_LONG);
        toast.show();
        updateList();
    }

    public void EditPetSuccessful() {
        setContentView(R.layout.activity_pets);
        Toast toast = Toast.makeText(this, "Animal modificado", Toast.LENGTH_LONG);
        toast.show();
        updateList();
    }

}