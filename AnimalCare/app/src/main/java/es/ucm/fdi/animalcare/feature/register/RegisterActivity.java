package es.ucm.fdi.animalcare.feature.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;

public class RegisterActivity extends BaseActivity implements RegisterView{
    private RegisterPresenter mRegisterPresenter;
    private Button mRegistrarUsuario;
    private EditText mUsername, mPassword, mPasswordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterPresenter = new RegisterPresenter(this);

        mUsername = findViewById(R.id.editText_register_username);
        mPassword = findViewById(R.id.editText_register_password);
        mPasswordRepeat = findViewById(R.id.editText_register_password_repeat);

        mRegistrarUsuario = findViewById(R.id.button_registrar_usuario);
        mRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRegisterPresenter.validateRegister(String.valueOf(mUsername.getText()), String.valueOf(mPassword.getText()), String.valueOf(mPasswordRepeat.getText())) ;
            }
        });
    }

    @Override
    public void registerSuccessful() {
        //launch pantalla principal
    }

    @Override
    public void registerFailureUsername() {
        Toast toast = Toast.makeText(this, "Ese username ya está cogido.", Toast.LENGTH_LONG);
        toast.show();
        mUsername.setText("");
    }

    @Override
    public void registerFailurePasswords() {
        Toast toast = Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG);
        toast.show();
        mPassword.setText("");
        mPasswordRepeat.setText("");
    }


}