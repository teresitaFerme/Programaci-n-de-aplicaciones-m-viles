package es.ucm.fdi.animalcare.feature.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
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
import es.ucm.fdi.animalcare.session.SessionHandler;

public class LoginActivity extends BaseActivity implements LoginView{
    public static final String PREFS_NAME = "Settings";
    private LoginPresenter mLoginPresenter;
    private EditText mUsername, mPassword;
    private Button mIniciasesion;
    private TextView mRegister;
    private Resources resources;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter = new LoginPresenter(this);
        App.getApp().setResources(getResources());
        bindViews();

        recover();
        resources = App.getApp().getResources();
        mIniciasesion.setText(App.getApp().getResources().getString(R.string.iniciar_sesion_button));
        TextView registerInfo = findViewById(R.id.login_register_info);
        registerInfo.setText(App.getApp().getResources().getString(R.string.aun_no_tienes_cuenta));
        mRegister.setText(App.getApp().getResources().getString(R.string.reg_strate));

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
    public void loginSuccessfull() {
        App.getApp().setPass(String.valueOf(mPassword.getText()));

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editoor = settings.edit();
        editoor.putBoolean("darkMode", App.getApp().getDarkMode());
        editoor.putString("language", App.getApp().getLanguage());
        editoor.putString("nombre" , App.getApp().getUser().getmUsername());
        editoor.putString("pass", App.getApp().getPass());

        editoor.commit();

        sp = getSharedPreferences(SessionHandler.getSPname(), MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", App.getApp().getUserName());
        editor.putString("name", User.getInstance(null, null, 0).getmName());
        editor.apply();

        Intent intent = new Intent(this, PetsActivity.class);
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
        App.getApp().setPass(settings.getString("pass", ""));

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
            if(!App.getApp().getPass().equals("")){
                mPassword.setText(App.getApp().getPass());
                mLoginPresenter.validateLogin(String.valueOf(mUsername.getText()), String.valueOf(mPassword.getText()));
            }
        }
        if(App.getApp().getDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }


    @Override
    public void bindViews() {
        mUsername = findViewById(R.id.editText_username);
        mPassword = findViewById(R.id.editText_password);
        mRegister = findViewById(R.id.login_register_option);
        mIniciasesion = findViewById(R.id.button_inicia_sesion);
    }
}