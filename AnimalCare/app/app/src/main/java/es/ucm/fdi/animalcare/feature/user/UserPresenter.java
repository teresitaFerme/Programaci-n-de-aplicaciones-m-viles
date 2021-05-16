package es.ucm.fdi.animalcare.feature.user;

import android.content.Context;
import android.content.SharedPreferences;

import es.ucm.fdi.animalcare.base.BasePresenter;

public class UserPresenter extends BasePresenter {
    private UserView mUserView;
    private UserModel mUserModel;

    UserPresenter(UserView view){
        mUserView = view;
        mUserModel = new UserModel((Context) mUserView);
    }

    public boolean validateName(String username, String name){
        if (username != null && !name.isEmpty()){
            // Subir el cambio a la BBDD
            mUserModel.setName(username, name);
            mUserView.changeSuccessful();
            return true;
        }
        mUserView.fillField();
        return false;
    }
}
