package es.ucm.fdi.animalcare.feature.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.login.LoginActivity;
import es.ucm.fdi.animalcare.feature.password.PasswordActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.session.SessionHandler;

public class UserActivity extends BaseActivity implements UserView, ToolBarManagement {
    private UserPresenter mUserPresenter;
    private EditText mNameView;
    private Button mLogoutButton;
    private ImageView mIconEdit;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.white));

        mNameView = findViewById(R.id.name);
        mLogoutButton = findViewById(R.id.button_logout);

        sp = getSharedPreferences(SessionHandler.getSPname(), MODE_PRIVATE);
        String mName = sp.getString("name", "User");
        mNameView.setText(mName);

        mIconEdit = findViewById(R.id.icon_edit);

        mUserPresenter = new UserPresenter(this);
    }

    @Override
    public void logout(View view){
        // Cerrar sesión
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void changeName(View view){
        // Habilitar el EditText del nombre de usuario
        mNameView.setEnabled(true);

        // Cambiar el icono
        mIconEdit.setImageDrawable(getDrawable(R.drawable.ic_confirm));

        // Fijar el onClick del icono a confirmName()
        mIconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmName(view);
            }
        });
    }

    @Override
    public void confirmName(View view){
        String mUsername = sp.getString("username", null);
        String mName = mNameView.getText().toString();

        // Validar las variables y subirlo a la BBDD
        if(mUserPresenter.validateName(mUsername, mName)) {
            // Cambiar el nombre del usuario en la sesión
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name", mName);
            editor.apply();
        }

        // Deshabilitar el EditText del nombre de usuario
        mNameView.setEnabled(false);

        // Cambiar el icono
        mIconEdit.setImageDrawable(getDrawable(R.drawable.ic_edit));

        // Fijar el onClick del icono a changeName()
        mIconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeName(view);
            }
        });
    }

    public void changePassword(View view){
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void fillField() {
        Toast toast = Toast.makeText(this, "Por favor, rellene el campo.", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void changeSuccessful(){
        Toast toast = Toast.makeText(this, "Su nombre se ha cambiado con éxito.", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void launchFromToolbar(View view) {
        if(view.getId() != R.id.button_toolbar_user){
            Intent intent;
            switch (view.getId()){
                case R.id.button_toolbar_pets:
                    intent = new Intent(this, PetsActivity.class);
                    break;
                case R.id.button_toolbar_upcoming:
                    intent = new Intent(this, UpcomingActivity.class);
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
}