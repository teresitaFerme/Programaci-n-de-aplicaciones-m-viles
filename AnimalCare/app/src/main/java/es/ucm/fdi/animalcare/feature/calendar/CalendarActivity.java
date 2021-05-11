package es.ucm.fdi.animalcare.feature.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class CalendarActivity extends BaseActivity implements CalendarView, ToolBarManagement {
    private User user;
    private CalendarPresenter mCalendarPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.iconColor));

        user = (User) getIntent().getSerializableExtra("user");

        mCalendarPresenter = new CalendarPresenter(this);
    }

    @Override
    public void launchFromToolbar(View view) {
        if(view.getId() != R.id.button_toolbar_calendar){
            Intent intent;
            switch (view.getId()){
                case R.id.button_toolbar_upcoming:
                    intent = new Intent(this, UpcomingActivity.class);
                    break;
                case R.id.button_toolbar_user:
                    intent = new Intent(this, UserActivity.class);
                    break;
                case R.id.button_toolbar_pets:
                    intent = new Intent(this, PetsActivity.class);
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
}