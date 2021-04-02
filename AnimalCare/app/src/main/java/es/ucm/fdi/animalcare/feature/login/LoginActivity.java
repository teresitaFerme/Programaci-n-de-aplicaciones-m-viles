package es.ucm.fdi.animalcare.feature.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;

public class LoginActivity extends BaseActivity implements LoginView{
    private LoginPresenter mLoginPresenter;
    private EditText mUsername, mPassword;
    private Button mIniciasesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter = new LoginPresenter(this);

        mUsername = findViewById(R.id.editText_username);
        mPassword = findViewById(R.id.editText_password);

        mIniciasesion = findViewById(R.id.button_inicia_sesion);
        mIniciasesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.validateLogin(String.valueOf(mUsername.getText()), String.valueOf(mPassword.getText()));
            }
        });
    }

    @Override
    public void loginSuccessfull() {
        Intent intent = new Intent(this, PetsActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailure() {
        Toast toast = Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG);
        toast.show();
        mPassword.setText("");
    }
}