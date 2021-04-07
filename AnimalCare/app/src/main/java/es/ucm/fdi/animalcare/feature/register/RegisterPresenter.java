package es.ucm.fdi.animalcare.feature.register;

import android.content.Context;

import es.ucm.fdi.animalcare.base.BasePresenter;

public class RegisterPresenter extends BasePresenter {
    private RegisterView mRegisterView;
    private RegisterModel mRegisterModel;

    RegisterPresenter(RegisterView registerView){
        mRegisterModel = new RegisterModel((Context) registerView);
        mRegisterView = registerView;
    }

    public void validateRegister(String username, String password, String passwordRepeat){
        if(username.length() == 0 || password.length() == 0 || passwordRepeat.length() == 0){
            mRegisterView.fillFields();
        }else if(mRegisterModel.getUsername(username)) mRegisterView.registerFailureUsername();
        else if(!password.equals(passwordRepeat)) mRegisterView.registerFailurePasswords();
        else if(mRegisterModel.registerUser(name, username, password)) mRegisterView.registerSuccessful();
    }
}
