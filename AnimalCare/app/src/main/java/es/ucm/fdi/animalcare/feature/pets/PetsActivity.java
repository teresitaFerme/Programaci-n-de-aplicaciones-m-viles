package es.ucm.fdi.animalcare.feature.pets;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import android.widget.Toast;
import android.widget.Toolbar;

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
    private Button mAddPet;
    private PetsAdapter mPetAdapter;
    private List<Pets> listPets;
    private RecyclerView recyclerView;
    private ConstraintLayout toolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        mPetList = findViewById(R.id.PetsList);
        mAddPet = findViewById(R.id.AddPet);
        toolbar = findViewById(R.id.toolbar_pets);

        setUpToolbar();


        user = (User) getIntent().getSerializableExtra("user");

        mPetsPresenter = new PetsPresenter(this);



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
                viewPet(listPets.get(recyclerView.getChildAdapterPosition(view)).getName(),listPets.get(recyclerView.getChildAdapterPosition(view)).getType(), listPets.get(recyclerView.getChildAdapterPosition(view)).getId());
            }
        });
        recyclerView.setAdapter(mPetAdapter);

        mAddPet.setText(App.getApp().getResources().getString(R.string.anadir_mascota));
        mAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPet();
            }
        });

    }

    public void addNewPet() {
        Intent intent = new Intent(this, NewPetsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        updateList();
    }

    public void viewPet(String name,String type, Integer id){
        Intent intent = new Intent(this, ProfilePetActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        startActivity(intent);
        updateList();
    }

    @Override
    public void noRegister() {
        Toast toast = Toast.makeText(this, "Logeate para mostrar tus mascotas", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void bindViews() {

    }

    @Override
    public void setUpToolbar() {
        toolbar.findViewById(R.id.button_toolbar_pets).getBackground().setTint(App.getApp().getResources().getColor(R.color.white));
        toolbar.findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        toolbar.findViewById(R.id.button_toolbar_settings).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        toolbar.findViewById(R.id.button_toolbar_calendar).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        toolbar.findViewById(R.id.button_toolbar_user).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
    }
}