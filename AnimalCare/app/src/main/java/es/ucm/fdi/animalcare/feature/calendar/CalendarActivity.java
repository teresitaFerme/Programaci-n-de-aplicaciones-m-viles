package es.ucm.fdi.animalcare.feature.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.base.BasePresenter;

public class CalendarActivity extends BaseActivity implements CalendarView {
    private CalendarPresenter mCalendarPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mCalendarPresenter = new CalendarPresenter(this);
    }
}