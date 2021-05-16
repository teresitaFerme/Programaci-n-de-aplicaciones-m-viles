package es.ucm.fdi.animalcare.feature.pets;

import es.ucm.fdi.animalcare.base.BaseView;

public interface PetsView extends BaseView {
    void addNewPet();
    void viewPet(String name,String type, Integer id);
    void noRegister();
}
