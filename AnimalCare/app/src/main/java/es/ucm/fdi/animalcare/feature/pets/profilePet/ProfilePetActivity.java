package es.ucm.fdi.animalcare.feature.pets.profilePet;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;

public class ProfilePetActivity extends ToolBarManagement implements ProfilePetView {
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
    private ProfilePetAdapter taskAdapter;
    private List<Task> taskList;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_profile);

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

        Resources resources = App.getApp().getResources();
        mEditPet.setText(resources.getString(R.string.editar_boton));
        mDeletePet.setText(resources.getString(R.string.eliminar_boton));

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
                mProfilePetPresenter.deletePet(id);
            }
        });

    }

    public void imagePet (ImageView image, String type){

        if(type.equals("Perro") || type.equals("Dog")){
            image.setImageResource(R.drawable.dog_green);}
        else if(type.equals("Gato") || type.equals("Cat")){
            image.setImageResource(R.drawable.cat_green); }
        else if(type.equals("Pajaro") || type.equals("Bird")){
            image.setImageResource(R.drawable.bird_green);}
        else if(type.equals("Caballo") || type.equals("Horse")){
            image.setImageResource(R.drawable.horse_green);}
        else if(type.equals("Pez") || type.equals("Fish")){
            image.setImageResource(R.drawable.fish_green);}
        else if(type.equals("Tortuga") || type.equals("Turtle")){
            image.setImageResource(R.drawable.turtle_green);}
        else{image.setImageResource(R.drawable.dog_green);}

    }

    public void  updateList(){
        taskList = new ArrayList<>();
        taskList = mProfilePetPresenter.getAllPetTasks(id);

        mTaskListView = findViewById(R.id.taskListPetView);
        mTaskListView.setLayoutManager(new LinearLayoutManager(this));
        mTaskListView.setHasFixedSize(true);
        taskAdapter = new ProfilePetAdapter( taskList, this);
        mTaskListView.setAdapter(taskAdapter);
    }

    public void deletePet(Integer id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(App.getApp().getResources().getString(R.string.remove_pet_dialog));
        alertDialog.setPositiveButton(getResources().getString(R.string.dialog_yes), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                mProfilePetPresenter.validateDeletePet(id);
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.dialog_no), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing
            }
        });
        alertDialog.show();
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

        mTypePet.setSelection(num_type(type));

        mNewPet = findViewById(R.id.button_newpet_add);
        mNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfilePetPresenter.validateEditPet(id, String.valueOf(mNamePet.getText()), mTypePet.getSelectedItem().toString(), App.getApp().getUser().getmId()) ;
            }
        });

    }

    public void returnPets(){
        Intent intent = new Intent(this, PetsActivity.class);
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

    public int num_type (String type){
        int value = 0;
        if(type.equals("Perro") || type.equals("Dog")){ value = 0; }
        else if(type.equals("Gato") || type.equals("Cat")){ value = 1; }
        else if(type.equals("Pajaro") || type.equals("Bird")){ value =2;}
        else if(type.equals("Pez") || type.equals("Fish")){ value =3;}
        else if(type.equals("Tortuga") || type.equals("Turtle")){ value = 4;}
        else if(type.equals("Caballo") || type.equals("Horse")){ value =5;}
        return value;
    }

}