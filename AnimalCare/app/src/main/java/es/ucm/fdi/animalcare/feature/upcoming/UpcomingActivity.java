package es.ucm.fdi.animalcare.feature.upcoming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.base.BaseView;

public class UpcomingActivity extends BaseActivity implements UpcomingView {
    private UpcomingPresenter mUpcomingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        mUpcomingPresenter = new UpcomingPresenter(this);
    }
}