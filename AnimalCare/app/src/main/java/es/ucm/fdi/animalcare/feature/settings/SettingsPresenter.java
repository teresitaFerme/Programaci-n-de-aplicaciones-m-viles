package es.ucm.fdi.animalcare.feature.settings;

import androidx.appcompat.app.AppCompatDelegate;

import es.ucm.fdi.animalcare.base.BasePresenter;
import es.ucm.fdi.animalcare.data.App;

public class SettingsPresenter extends BasePresenter {
    private SettingsView mSettingsView;
    private SettingsModel mSettingsModel;

    SettingsPresenter(SettingsView view){
        mSettingsView = view;
        mSettingsModel = new SettingsModel();
    }

    public void screenModeChanged() {
        if(!App.getApp().getDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public void notificationsEnabled() {
    }
}
