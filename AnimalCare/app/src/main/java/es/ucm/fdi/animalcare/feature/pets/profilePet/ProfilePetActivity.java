package es.ucm.fdi.animalcare.feature.pets.profilePet;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsPresenter;
import es.ucm.fdi.animalcare.feature.pets.newPets.NewPetsPresenter;
import es.ucm.fdi.animalcare.feature.pets.newPets.NewPetsView;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.TaskAdapter;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingPresenter;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class ProfilePetActivity extends BaseActivity implements ProfilePetView, ToolBarManagement {

    private User user;
    private String name;
    private String type;
    private Integer id;
    private EditText mNamePet;
    private Spinner mTypePet;
    private Button mNewPet;
    private TextView mShowName;
    private ImageView mShowImage;
    private Button mEditPet;
    private Button mDeletePet;
    private ProfilePetPresenter mProfilePetPresenter;
    private RecyclerView mTaskListView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_profile);

        user = (User) getIntent().getSerializableExtra("user");
        name = (String) getIntent().getSerializableExtra("name");
        type = (String) getIntent().getSerializableExtra("type");
        id = (Integer) getIntent().getSerializableExtra("id");

        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.iconColor));

        mProfilePetPresenter = new ProfilePetPresenter(this)  ;

        mShowName = findViewById(R.id.NamePetView);
        mShowImage = findViewById(R.id.imgPet);
        mEditPet = findViewById(R.id.buttonEditPet);
        mDeletePet = findViewById(R.id.buttonDeletePet);

        mShowName.setText(name);
        imagePet(mShowImage,type);

        updateList();

        mEditPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfilePetPresenter.editPet(id);
            }
        });

        mDeletePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfilePetPresenter.validateDeletePet(id);
            }
        });

    }

    public void imagePet (ImageView image, String type){

        if(type.equals("Perro")){
            image.setImageResource(R.drawable.dog_green);}
        else if(type.equals("Gato")){
            image.setImageResource(R.drawable.cat_green); }
        else if(type.equals("Pajaro")){
            image.setImageResource(R.drawable.bird_green);}
        else if(type.equals("Caballo")){
            image.setImageResource(R.drawable.horse_green);}
        else if(type.equals("Pez")){
            image.setImageResource(R.drawable.fish_green);}
        else if(type.equals("Tortuga")){
            image.setImageResource(R.drawable.turtle_green);}
        else{image.setImageResource(R.drawable.dog_green);}

    }

    public void  updateList(){
        taskList = new ArrayList<>();
        taskList = mProfilePetPresenter.getAllPetTasks(id);

        mTaskListView = findViewById(R.id.taskListPetView);
        mTaskListView.setLayoutManager(new LinearLayoutManager(this));
        mTaskListView.setHasFixedSize(true);
        taskAdapter = new TaskAdapter( taskList, this);
        mTaskListView.setAdapter(taskAdapter);
    }

    @Override
    public void editPet(Integer id) {
        setContentView(R.layout.activity_pets_new);

        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.iconColor));

        mProfilePetPresenter = new ProfilePetPresenter(this);

        mNamePet = findViewById(R.id.editText_newpet_name);
        mTypePet = findViewById(R.id.spinner);


        Resources res = getResources();
        String[] options = res.getStringArray(R.array.Pets_animals);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        mTypePet.setAdapter(adapter);

        mNewPet = findViewById(R.id.button_newpet_add);
        mNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfilePetPresenter.validateEditPet(id, String.valueOf(mNamePet.getText()), mTypePet.getSelectedItem().toString(), user.getmId()) ;
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
            intent.putExtra("user", user);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void returnPets(){
        Intent intent = new Intent(this, PetsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void EditPetSuccessful() {
        Toast toast = Toast.makeText(this, R.string.edit_pet, Toast.LENGTH_LONG);
        toast.show();
        returnPets();
    }

    @Override
    public void DeletePetSuccessful() {
        Toast toast = Toast.makeText(this, R.string.del_pet, Toast.LENGTH_LONG);
        toast.show();
        returnPets();
    }

    @Override
    public void DeletePetError() {
        Toast toast = Toast.makeText(this, R.string.error_del_pet, Toast.LENGTH_LONG);
        toast.show();
        returnPets();
    }

    @Override
    public void EditPetError() {
        Toast toast = Toast.makeText(this, R.string.error_edit_pet, Toast.LENGTH_LONG);
        toast.show();
        returnPets();
    }

    @Override
    public void fillField() {
        Toast toast = Toast.makeText(this, R.string.fill_please, Toast.LENGTH_LONG);
        toast.show();
    }


}
