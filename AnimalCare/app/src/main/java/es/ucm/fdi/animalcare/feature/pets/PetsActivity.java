package es.ucm.fdi.animalcare.feature.pets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;

public class PetsActivity extends BaseActivity implements PetsView{
    private PetsPresenter mPetsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        mPetsPresenter = new PetsPresenter(this);
    }
}