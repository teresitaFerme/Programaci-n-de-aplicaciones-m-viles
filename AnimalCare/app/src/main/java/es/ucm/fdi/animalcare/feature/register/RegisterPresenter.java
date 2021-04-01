package es.ucm.fdi.animalcare.feature.register;

import es.ucm.fdi.animalcare.base.BasePresenter;

public class RegisterPresenter extends BasePresenter {
    private RegisterView mRegisterView;
    private RegisterModel mRegisterModel;

    RegisterPresenter(RegisterView registerView){
        mRegisterModel = new RegisterModel();
        mRegisterView = registerView;
    }

    public void validateRegister(String username, String password, String passwordRepeat){
        if(mRegisterModel.getUsername(username)) mRegisterView.registerFailureUsername();
        else if(!password.equals(passwordRepeat)) mRegisterView.registerFailurePasswords();
        else mRegisterView.registerSuccessful();
        //AQUI HABRIA QUE COMPROBAR QUE TODOS LOS CAMPOS TIENEN ALGO, añadir ese metodo en el registerView

    }
}
