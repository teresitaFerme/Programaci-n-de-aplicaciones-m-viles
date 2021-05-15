package es.ucm.fdi.animalcare.feature.settings;

import androidx.appcompat.widget.SwitchCompat;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.LocaleHelper;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class SettingsActivity extends BaseActivity implements SettingsView, ToolBarManagement {
    private User user;
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

        user = (User) getIntent().getSerializableExtra("user");

        updateLanguage();
    }

    @Override
    public void launchFromToolbar(View view) {
        if(view.getId() != R.id.button_toolbar_settings){
            Intent intent;
            switch (view.getId()){
                case R.id.button_toolbar_pets:
                    intent = new Intent(this, PetsActivity.class);
                    break;
                case R.id.button_toolbar_user:
                    intent = new Intent(this, UserActivity.class);
                    break;
                case R.id.button_toolbar_calendar:
                    intent = new Intent(this, CalendarActivity.class);
                    break;
                default:
                    intent = new Intent(this, UpcomingActivity.class);
                    break;

            }
            intent.putExtra("user", user);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void bindViews(){
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

    private void setUpToolbar(){
        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.iconColor));
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
            }
        });

        mNotificationsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingsPresenter.notificationsEnabled();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Locale locale;
                if(i == R.id.settings_spanish_language){
                    context = LocaleHelper.setLocale(SettingsActivity.this, "es");
                    locale = new Locale("es");
                }else{
                    context = LocaleHelper.setLocale(SettingsActivity.this, "en");
                    locale = new Locale("es");
                }
                resources = context.getResources();
                App.getApp().setResources(resources);
                updateLanguage();
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
}