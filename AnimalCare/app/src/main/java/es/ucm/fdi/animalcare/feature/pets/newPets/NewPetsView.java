package es.ucm.fdi.animalcare.feature.pets.newPets;

import es.ucm.fdi.animalcare.base.BaseView;

public interface NewPetsView extends BaseView {

    void NewPetSuccessful();

    void fillField();

    void NewPetError();
}
