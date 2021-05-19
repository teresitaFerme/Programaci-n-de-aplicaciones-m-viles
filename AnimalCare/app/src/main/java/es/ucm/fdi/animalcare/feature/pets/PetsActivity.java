package es.ucm.fdi.animalcare.feature.pets;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.Pets;
import es.ucm.fdi.animalcare.feature.pets.newPets.NewPetsActivity;
import es.ucm.fdi.animalcare.feature.pets.profilePet.ProfilePetActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;

public class PetsActivity extends ToolBarManagement implements PetsView {
    private PetsPresenter mPetsPresenter;
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

        bindViews();
        setUpToolbar();

        mPetsPresenter = new PetsPresenter(this);
        updateList();
    }

    public void  updateList(){
        listPets = new ArrayList<>();
        listPets = mPetsPresenter.validateUserPets(App.getApp().getUser().getmId());
        App.getApp().getUser().setmPetList(listPets);

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
        startActivity(intent);
        updateList();
    }

    public void viewPet(String name,String type, Integer id){
        Intent intent = new Intent(this, ProfilePetActivity.class);
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
        mAddPet = findViewById(R.id.AddPet);
        toolbar = findViewById(R.id.toolbar_pets);
        recyclerView = findViewById(R.id.PetsList);
    }

    public void setUpToolbar() {
        super.setUpToolbar(R.id.button_toolbar_pets);
    }
}