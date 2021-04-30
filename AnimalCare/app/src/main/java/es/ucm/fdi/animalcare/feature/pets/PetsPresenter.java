package es.ucm.fdi.animalcare.feature.pets;

import android.content.Context;

import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BasePresenter;
import es.ucm.fdi.animalcare.data.Pets;

public class PetsPresenter extends BasePresenter {
    private PetsView mPetsView;
    private PetsModel mPetsModel;

    PetsPresenter(PetsView petsView){
        this.mPetsView = petsView;
        mPetsModel = new PetsModel((Context) petsView);
    }

    public void validateNewPet(String name, String type){
        if(name.length() == 0 && type.length() == 0) mPetsView.fillField();
        else{
            mPetsModel.saveNewPet(name,type);
            mPetsView.NewPetSuccessful();

        }
    }

    public void addNewPet() {
        mPetsView.addNewPet();
    }

    public List<Pets> validateUserPets(String s) {
        if(s == null){
            mPetsView.noRegister();
        }
        return mPetsModel.getPets(Integer.parseInt(s));
    }
}
