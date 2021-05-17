package es.ucm.fdi.animalcare.feature.pets.newPets;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsPresenter;
import es.ucm.fdi.animalcare.feature.pets.PetsView;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class NewPetsActivity extends BaseActivity implements NewPetsView, ToolBarManagement {
    private User user;
    private NewPetsPresenter mNewPetsPresenter;
    private EditText mNamePet;
    private Spinner mTypePet;
    private Button mNewPet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_new);
        setUpToolbar();
        bindViews();

        user = (User) getIntent().getSerializableExtra("user");

        mNewPetsPresenter = new NewPetsPresenter(this);

        Resources res = getResources();
        String[] options = res.getStringArray(R.array.Pets_animals);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        mTypePet.setAdapter(adapter);

        mNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewPetsPresenter.validateNewPet(String.valueOf(mNamePet.getText()), mTypePet.getSelectedItem().toString(), user.getmId()) ;
            }
        });

    }

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
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void bindViews() {
        mNamePet = findViewById(R.id.editText_newpet_name);
        mTypePet = findViewById(R.id.spinner);
        mNewPet = findViewById(R.id.button_newpet_add);
    }

    @Override
    public void setUpToolbar() {
        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.iconColor));
    }
}
