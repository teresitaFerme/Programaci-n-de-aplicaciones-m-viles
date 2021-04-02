package es.ucm.fdi.animalcare.feature.user;

import es.ucm.fdi.animalcare.base.BasePresenter;

public class UserPresenter extends BasePresenter {
    private UserView mUserView;
    private UserModel mUserModel;

    UserPresenter(UserView view){
        mUserModel = new UserModel();
        mUserView = view;
    }
}
