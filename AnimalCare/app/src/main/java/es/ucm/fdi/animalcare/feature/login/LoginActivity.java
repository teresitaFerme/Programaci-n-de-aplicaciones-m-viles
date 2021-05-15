package es.ucm.fdi.animalcare.feature.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.LocaleHelper;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.register.RegisterActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.session.SessionHandler;

public class LoginActivity extends BaseActivity implements LoginView{
    public static final String PREFS_NAME = "Settings";
    private LoginPresenter mLoginPresenter;
    private EditText mUsername, mPassword;
    private Button mIniciasesion;
    private TextView mRegister;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter = new LoginPresenter(this);
        App.getApp().setResources(getResources());

        mUsername = findViewById(R.id.editText_username);
        mPassword = findViewById(R.id.editText_password);
        mRegister = findViewById(R.id.login_register_option);

        recover();

        mIniciasesion = findViewById(R.id.button_inicia_sesion);
        mIniciasesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.validateLogin(String.valueOf(mUsername.getText()), String.valueOf(mPassword.getText()));
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.launchRegister();
            }
        });

        sp = getSharedPreferences(SessionHandler.getSPname(), MODE_PRIVATE);
    }

    @Override
    public void loginSuccessfull(User u) {
        App.getApp().setUserName(u.getmName());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", u.getmUsername());
        editor.putString("name", u.getmName());
        editor.apply();

        Intent intent = new Intent(this, PetsActivity.class);
        intent.putExtra("user", u);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailure() {
        Toast toast = Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG);
        toast.show();
        mPassword.setText("");
    }

    @Override
    public void launchRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


    private void recover(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        App.getApp().setLanguage(settings.getString("language", "es"));
        App.getApp().setUserName(settings.getString("nombre", ""));
        App.getApp().setDarkMode(settings.getBoolean("darkMode", false));

        Context context;
        Locale locale;
        if(App.getApp().getLanguage().equals("es")){
            context = LocaleHelper.setLocale(LoginActivity.this, "es");
            locale = new Locale("es");
            App.getApp().setLanguage("es");
        }else{
            context = LocaleHelper.setLocale(LoginActivity.this, "en");
            locale = new Locale("es");
            App.getApp().setLanguage("en");
        }
        App.getApp().setResources(context.getResources());
        if(!App.getApp().getUserName().equals("")){
            mUsername.setText(App.getApp().getUserName());
        }
        if(App.getApp().getDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }


}