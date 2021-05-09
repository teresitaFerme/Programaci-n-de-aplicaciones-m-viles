package es.ucm.fdi.animalcare.feature.user;

import android.view.View;

import es.ucm.fdi.animalcare.base.BaseView;

public interface UserView extends BaseView {
    void logout(View view);
    void changeName(View view);
    void confirmName(View view);
    void fillField();
    void changeSuccessful();
}
