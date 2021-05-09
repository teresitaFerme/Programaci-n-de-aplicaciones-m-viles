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

    public void setName(String username, String name){
        mUserModel.setName(username, name);
    }
}
