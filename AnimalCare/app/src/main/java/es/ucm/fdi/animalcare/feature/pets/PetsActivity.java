package es.ucm.fdi.animalcare.feature.pets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class PetsActivity extends BaseActivity implements PetsView, ToolBarManagement {
    private PetsPresenter mPetsPresenter;
    private ImageView mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        getIntent();

        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.iconColor));

        mPetsPresenter = new PetsPresenter(this);
    }

    @Override
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
            startActivity(intent);
        }
    }
}