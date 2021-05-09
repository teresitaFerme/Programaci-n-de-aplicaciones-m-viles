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

    public void validateNewPet(String name, String type, Integer userId){
        if(name.length() == 0 && type.length() == 0) mPetsView.fillField();
        else {
            mPetsModel.saveNewPet(name, type, userId);
            mPetsView.NewPetSuccessful();

        }
    }

    public void addNewPet() {
        mPetsView.addNewPet();
    }

    public List<Pets> validateUserPets(Integer userId) {
        if(userId == null){
            mPetsView.noRegister();
        }
        return mPetsModel.getPets(userId);
    }

    public void validateDeletePet(Integer petId) {
        mPetsModel.deletePet(petId);
        mPetsView.DeletePetSuccessful();
    }

    public void validateEditPet(Integer petId, String name, String type, Integer user) {
        mPetsModel.editPet(petId, name, type, user);
        mPetsView.EditPetSuccessful();
    }

    public void editPet(Integer id) {
            mPetsView.editPet(id);
    }
}
