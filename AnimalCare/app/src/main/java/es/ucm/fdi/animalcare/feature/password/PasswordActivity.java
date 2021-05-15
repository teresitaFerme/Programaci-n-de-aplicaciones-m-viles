package es.ucm.fdi.animalcare.feature.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.feature.register.RegisterView;
import es.ucm.fdi.animalcare.session.SessionHandler;

public class PasswordActivity extends BaseActivity implements PasswordView {
    private PasswordPresenter mPasswordPresenter;
    private EditText mOldPW, mNewPW1, mNewPW2;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        mOldPW = findViewById(R.id.old_pw);
        mNewPW1 = findViewById(R.id.new_pw1);
        mNewPW2 = findViewById(R.id.new_pw2);

        mPasswordPresenter = new PasswordPresenter(this);

        sp = getSharedPreferences(SessionHandler.getSPname(), MODE_PRIVATE);
    }

    @Override
    public void confirmPassword(View view){
        String mUsername = sp.getString("username", null);

        String mopw = mOldPW.getText().toString();
        String mnpw1 = mNewPW1.getText().toString();
        String mnpw2 = mNewPW2.getText().toString();

        mPasswordPresenter.validatePasswords(mUsername, mopw, mnpw1, mnpw2);

        finish();
    }

    @Override
    public void fillField() {
        Toast toast = Toast.makeText(this, R.string.password_fill_field, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void incorrectPassword(){
        Toast toast = Toast.makeText(this, R.string.password_incorrect_password, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void noPasswordMatch(){
        Toast toast = Toast.makeText(this, R.string.password_no_match, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void changeSuccessful(){
        Toast toast = Toast.makeText(this, R.string.password_change_successful, Toast.LENGTH_LONG);
        toast.show();
    }

    public void goBack (View view){
        finish();
    }
}