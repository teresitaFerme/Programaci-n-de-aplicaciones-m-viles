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

    public void validateRegister(String name, String username, String password, String passwordRepeat){
        if(mRegisterModel.getUsername(username)) mRegisterView.registerFailureUsername();
        else if(!password.equals(passwordRepeat)) mRegisterView.registerFailurePasswords();
        else if(mRegisterModel.registerUser(name, username, password)) mRegisterView.registerSuccessful();
        //AQUI HABRIA QUE COMPROBAR QUE TODOS LOS CAMPOS TIENEN ALGO, añadir ese metodo en el registerView

    }
}
