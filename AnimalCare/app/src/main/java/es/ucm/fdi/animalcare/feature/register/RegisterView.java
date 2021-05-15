package es.ucm.fdi.animalcare.feature.register;

import es.ucm.fdi.animalcare.base.BaseView;
import es.ucm.fdi.animalcare.data.User;

public interface RegisterView extends BaseView {
    void registerSuccessful(User u);
    void registerFailureUsername();
    void registerFailurePasswords();
    void fillFields();
    //aqui deberiamos añadir un metodo para ver que las contraseñas sean suficientemente largas
}
