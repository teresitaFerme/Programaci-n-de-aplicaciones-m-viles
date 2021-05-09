package es.ucm.fdi.animalcare.feature.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.register.RegisterActivity;
import es.ucm.fdi.animalcare.session.SessionHandler;

public class LoginActivity extends BaseActivity implements LoginView{
    private LoginPresenter mLoginPresenter;
    private EditText mUsername, mPassword;
    private Button mIniciasesion;
    private TextView mRegister;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter = new LoginPresenter(this);

        mUsername = findViewById(R.id.editText_username);
        mPassword = findViewById(R.id.editText_password);
        mRegister = findViewById(R.id.login_register_option);

        mIniciasesion = findViewById(R.id.button_inicia_sesion);
        mIniciasesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.validateLogin(String.valueOf(mUsername.getText()), String.valueOf(mPassword.getText()));
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.launchRegister();
            }
        });

        sp = getSharedPreferences(SessionHandler.getSPname(), MODE_PRIVATE);
    }

    @Override
    public void loginSuccessfull(User u) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", u.getmUsername());
        editor.putString("name", u.getmName());
        editor.apply();

        Intent intent = new Intent(this, PetsActivity.class);
        intent.putExtra("user", u);
        startActivity(intent);
    }

    @Override
    public void loginFailure() {
        Toast toast = Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG);
        toast.show();
        mPassword.setText("");
    }

    @Override
    public void launchRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}