package es.ucm.fdi.animalcare.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.feature.login.LoginActivity;
import es.ucm.fdi.animalcare.feature.register.RegisterActivity;

public class MainActivity extends BaseActivity implements MainView{
    private MainPresenter mMainPresenter;
    private Button mIniciarSesion, mRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenter(this);

        mIniciarSesion = findViewById(R.id.button_init_sesion);
        mRegistrarse = findViewById(R.id.button_registrarse);

        mIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPresenter.iniciarSesion();
            }
        });

        mRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPresenter.registrarse();
            }
        });
        this.deleteDatabase("User.db");
    }


    @Override
    public void iniciarSesion() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void registrarse() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}