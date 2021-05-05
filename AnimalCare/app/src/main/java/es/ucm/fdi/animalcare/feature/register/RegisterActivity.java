package es.ucm.fdi.animalcare.feature.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;

public class RegisterActivity extends BaseActivity implements RegisterView{
    private RegisterPresenter mRegisterPresenter;
    private Button mRegistrarUsuario;
    private EditText mName, mUsername, mPassword, mPasswordRepeat;

    private final String sharedPrefName = "preferences";
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterPresenter = new RegisterPresenter(this);

        mName = findViewById(R.id.editText_register_name);
        mUsername = findViewById(R.id.editText_register_username);
        mPassword = findViewById(R.id.editText_register_password);
        mPasswordRepeat = findViewById(R.id.editText_register_password_repeat);

        mRegistrarUsuario = findViewById(R.id.button_registrar_usuario);
        mRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterPresenter.validateRegister(String.valueOf(mName.getText()), String.valueOf(mUsername.getText()), String.valueOf(mPassword.getText()), String.valueOf(mPasswordRepeat.getText())) ;
            }
        });

        sp = getSharedPreferences(sharedPrefName, MODE_PRIVATE);
    }

    @Override
    public void registerSuccessful(User u) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", u.getmName());
        editor.apply();

        Intent intent = new Intent(this, PetsActivity.class);
        intent.putExtra("user", u);
        startActivity(intent);
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

    @Override
    public void fillFields() {
        Toast toast = Toast.makeText(this, "Por favor, rellene todos los campos.", Toast.LENGTH_LONG);
        toast.show();
    }

}