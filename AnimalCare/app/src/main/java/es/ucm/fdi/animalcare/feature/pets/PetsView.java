package es.ucm.fdi.animalcare.feature.pets;

import es.ucm.fdi.animalcare.base.BaseView;

public interface PetsView extends BaseView {
    void addNewPet();
    void editPet(Integer id);
    void noRegister();
    void EditPetSuccessful();
    void DeletePetSuccessful();
    boolean fillField();
    void NewPetSuccessful();
}
