package es.ucm.fdi.animalcare.feature.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.login.LoginActivity;
import es.ucm.fdi.animalcare.feature.password.PasswordActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.feature.user.books.BooksActivity;
import es.ucm.fdi.animalcare.session.SessionHandler;

public class UserActivity extends BaseActivity implements UserView, ToolBarManagement {
    private User user;
    private UserPresenter mUserPresenter;
    private EditText mNameView;
    private TextView greetings;
    private Button mLogoutButton;
    private ImageView mIconEdit;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        setUpToolbar();
        bindViews();


        sp = getSharedPreferences(SessionHandler.getSPname(), MODE_PRIVATE);
        String mName = sp.getString("name", "User");
        mNameView.setText(mName);




        mUserPresenter = new UserPresenter(this);
        user = (User) getIntent().getSerializableExtra("user");
    }

    @Override
    public void logout(View view){

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editoor = settings.edit();
        editoor.putBoolean("darkMode", App.getApp().getDarkMode());
        editoor.putString("language", App.getApp().getLanguage());
        editoor.putString("nombre" , App.getApp().getUserName());
        editoor.putString("pass", "");

        editoor.commit();

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
            intent.putExtra("user", user);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    @Override
    public void bindViews() {
        mNameView = findViewById(R.id.name);
        mLogoutButton = findViewById(R.id.button_logout);
        mIconEdit = findViewById(R.id.icon_edit);
        greetings = findViewById(R.id.greeting);
        Button button_pw = findViewById(R.id.button_pw);
        Button urgencias = findViewById(R.id.call);
        Button gps = findViewById(R.id.gps);
        Button buscar = findViewById(R.id.buscar);

        Resources resources = App.getApp().getResources();

        greetings.setText(resources.getString(R.string.greetings));
        button_pw.setText(resources.getString(R.string.cambiar_contrase_a));
        mLogoutButton.setText(resources.getString(R.string.cerrar_sesi_n));
        urgencias.setText(resources.getString(R.string.llamar_a_nurgencias));
        gps.setText(resources.getString(R.string.localizar_ncl_nicas));
        buscar.setText(resources.getString(R.string.search_books_button));
        TextView bookInfo = findViewById(R.id.books_info);
        bookInfo.setText(resources.getString(R.string.dale_el_mejor_cuidado_a_tu_mascota_con_nuestros_libros));

    }

    @Override
    public void setUpToolbar() {
        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.white));
    }

    public void llamarUrgencias(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:915627769"));
        startActivity(i);
    }

    public void localizarClinicas(View view) {
        // Get the string indicating a location. Input is not validated; it is
        // passed to the location handler intact.
        String loc = "clínica+veterinaria";

        // Parse the location and create the intent.
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        // Find an activity to handle the intent, and start that activity.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    public void buscarLibros(View view){
        Intent intent = new Intent(this, BooksActivity.class);
        intent.putExtra("user", user);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}