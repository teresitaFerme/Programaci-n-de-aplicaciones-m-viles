package es.ucm.fdi.animalcare.feature.login;

import android.content.Context;

import es.ucm.fdi.animalcare.base.BasePresenter;
import es.ucm.fdi.animalcare.data.User;

public class LoginPresenter extends BasePresenter {
    private LoginModel mLoginModel;
    private LoginView mLoginView;

    LoginPresenter(LoginView loginView){
        this.mLoginView = loginView;
        mLoginModel = new LoginModel((Context) loginView);
    }

    public void validateLogin(String username, String password){
        User u = mLoginModel.validateLogin(username, password);
        if(u != null) mLoginView.loginSuccessfull(u);
        else mLoginView.loginFailure();
    }

    public void launchRegister() {
        mLoginView.launchRegister();
    }
}
