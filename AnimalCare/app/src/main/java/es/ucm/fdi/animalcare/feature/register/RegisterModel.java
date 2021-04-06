package es.ucm.fdi.animalcare.feature.register;

import es.ucm.fdi.animalcare.base.BaseModel;

public class RegisterModel extends BaseModel {

    public boolean getUsername(String username) {
        //aqui hacemos consulta a bbdd para ver si ese username ya está registrado
        //si ya está registrado devolvemos true
        if(username.equals("username")) return true;
        else return false;
    }

    public void registerUser() {
        //registrar en la bbdd el usuario

        //guargar en cache usuario
    }
}
