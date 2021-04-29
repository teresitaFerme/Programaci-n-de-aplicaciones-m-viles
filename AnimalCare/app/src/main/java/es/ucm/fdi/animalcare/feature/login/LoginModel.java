package es.ucm.fdi.animalcare.feature.login;

import es.ucm.fdi.animalcare.base.BaseModel;

public class LoginModel extends BaseModel {
    private String mUsername;
    private String mPassword;

    LoginModel(){
        mUsername = "username";
        mPassword = "password";
        //esto no es así realmente, aquí debería ir las llamddas a bbdd
    }

    public boolean validateLogin(String username, String password) {
        //esto realmente es comprobar que existe ese user en la bbdd
        if(mUsername.equals(username) && mPassword.equals(password)) return true;
        else return false;
    }
}
