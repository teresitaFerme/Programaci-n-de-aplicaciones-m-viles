package es.ucm.fdi.animalcare.feature.password;

import android.view.View;

public interface PasswordView {
    void confirmPassword(View view);
    void fillField();
    void incorrectPassword();
    void noPasswordMatch();
    void changeSuccessful();
}
