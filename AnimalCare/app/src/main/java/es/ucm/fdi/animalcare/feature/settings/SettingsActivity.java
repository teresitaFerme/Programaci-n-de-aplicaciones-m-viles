package es.ucm.fdi.animalcare.feature.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;

public class SettingsActivity extends BaseActivity implements SettingsView {
    private SettingsPresenter mSettingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSettingsPresenter = new SettingsPresenter(this);
    }
}