package es.ucm.fdi.animalcare.feature.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;

public class UserActivity extends BaseActivity implements UserView {
    private UserPresenter mUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mUserPresenter = new UserPresenter(this);
    }
}