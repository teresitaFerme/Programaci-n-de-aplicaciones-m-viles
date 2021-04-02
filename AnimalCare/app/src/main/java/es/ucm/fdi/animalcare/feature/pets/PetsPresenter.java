package es.ucm.fdi.animalcare.feature.pets;

import es.ucm.fdi.animalcare.base.BasePresenter;

public class PetsPresenter extends BasePresenter {
    private PetsView mPetsView;
    private PetsModel mPetsModel;

    PetsPresenter(PetsView petsView){
        mPetsModel = new PetsModel();
        mPetsView = petsView;
    }

}
