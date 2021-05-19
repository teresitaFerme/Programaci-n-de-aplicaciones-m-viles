package es.ucm.fdi.animalcare.feature.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import java.util.Locale;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.LocaleHelper;
import es.ucm.fdi.animalcare.feature.login.LoginActivity;
import es.ucm.fdi.animalcare.feature.password.PasswordActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;

public class SettingsActivity extends ToolBarManagement implements SettingsView  {
    private SettingsPresenter mSettingsPresenter;
    private SwitchCompat mScreenModeSwitch, mNotificationsSwitch;
    private Context context;
    private Resources resources;
    private TextView title, darkMode, selectLanguage, notifications, changePassword, logOut;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mSettingsPresenter = new SettingsPresenter(this);
        bindViews();

        if(getResources().getConfiguration().locale.equals(Locale.ENGLISH)){
            radioGroup.check(R.id.settings_english_language);
        }else  radioGroup.check(R.id.settings_spanish_language);

        setUpToolbar();
        setUpListeners();
        updateLanguage();
    }

    public void bindViews(){
        mScreenModeSwitch = findViewById(R.id.settings_switch_screen_mode);
        mNotificationsSwitch = findViewById(R.id.settings_switch_notifications);
        title = findViewById(R.id.settings_title);
        darkMode = findViewById(R.id.settings_screen_mode);
        notifications = findViewById(R.id.settings_enable_notifications);
        selectLanguage = findViewById(R.id.settings_select_language);
        changePassword = findViewById(R.id.settings_change_password);
        logOut = findViewById(R.id.settings_log_out);
        radioGroup = findViewById(R.id.settings_radio_group);
    }

    public void setUpToolbar() {
        super.setUpToolbar(R.id.button_toolbar_settings);
    }

    private void setUpListeners(){
        mScreenModeSwitch.setChecked(true);
        mScreenModeSwitch.setChecked(false);
        mScreenModeSwitch.setChecked(App.getApp().getDarkMode());

        mScreenModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                App.getApp().setDarkMode(b);
                mSettingsPresenter.screenModeChanged();
                saveSettings();
            }
        });

        mNotificationsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingsPresenter.notificationsEnabled();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword(view);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Locale locale;
                if(i == R.id.settings_spanish_language){
                    context = LocaleHelper.setLocale(SettingsActivity.this, "es");
                    locale = new Locale("es");
                    App.getApp().setLanguage("es");
                }else{
                    context = LocaleHelper.setLocale(SettingsActivity.this, "en");
                    locale = new Locale("es");
                    App.getApp().setLanguage("en");
                }
                resources = context.getResources();
                App.getApp().setResources(resources);
                updateLanguage();
                saveSettings();
            }
        });
    }

    private void updateLanguage() {
        title.setText(App.getApp().getResources().getString(R.string.settings_title));
        darkMode.setText(App.getApp().getResources().getString(R.string.modo_oscuro));
        notifications.setText(App.getApp().getResources().getString(R.string.activar_notificaciones));
        selectLanguage.setText(App.getApp().getResources().getString(R.string.seleccionar_idioma));
        changePassword.setText(App.getApp().getResources().getString(R.string.cambiar_contrase_a));
        logOut.setText(App.getApp().getResources().getString(R.string.cerrar_sesi_n));
    }

    public void changePassword(View view){
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
    }

    private void saveSettings(){
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editoor = settings.edit();
        editoor.putBoolean("darkMode", App.getApp().getDarkMode());
        editoor.putString("language", App.getApp().getLanguage());
        editoor.putString("nombre" , App.getApp().getUserName());
        editoor.putString("pass", App.getApp().getPass());
        editoor.commit();
    }

}