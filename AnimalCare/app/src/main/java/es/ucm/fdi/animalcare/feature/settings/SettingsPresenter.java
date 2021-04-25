package es.ucm.fdi.animalcare.feature.settings;

import es.ucm.fdi.animalcare.base.BasePresenter;

public class SettingsPresenter extends BasePresenter {
    private SettingsView mSettingsView;
    private SettingsModel mSettingsModel;

    SettingsPresenter(SettingsView view){
        mSettingsView = view;
        mSettingsModel = new SettingsModel();
    }

    public void screenModeChanged() {
    }

    public void notificationsEnabled() {
    }
}
