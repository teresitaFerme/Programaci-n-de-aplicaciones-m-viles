package es.ucm.fdi.animalcare.feature.login;

import es.ucm.fdi.animalcare.base.BaseView;
import es.ucm.fdi.animalcare.data.User;

public interface LoginView extends BaseView {
    void loginSuccessfull();
    void loginFailure();
    void launchRegister();
}
