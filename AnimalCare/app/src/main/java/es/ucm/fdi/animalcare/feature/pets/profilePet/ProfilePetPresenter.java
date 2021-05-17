package es.ucm.fdi.animalcare.feature.pets.profilePet;

import android.content.Context;

import java.util.List;

import es.ucm.fdi.animalcare.base.BasePresenter;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.feature.pets.newPets.NewPetsModel;
import es.ucm.fdi.animalcare.feature.pets.newPets.NewPetsView;

public class ProfilePetPresenter extends BasePresenter {
    private ProfilePetView mProfilePetView;
    private ProfilePetModel mProfilePetModel;

    ProfilePetPresenter(ProfilePetView profilePetView){
        this.mProfilePetView = profilePetView;
        mProfilePetModel = new ProfilePetModel((Context) profilePetView);
    }

    public void editPet(Integer id) {
        mProfilePetView.editPet(id);
    }

    public void validateDeletePet(Integer petId) {
        if(mProfilePetModel.deletePet(petId)){
            mProfilePetView.DeletePetSuccessful();
        }
        else{
            mProfilePetView.DeletePetError();
        }

    }

    public void validateEditPet(Integer petId, String name, String type, Integer user) {
        if(name.length() == 0 || type.length() == 0) mProfilePetView.fillField();
        else {
            if (mProfilePetModel.editPet(petId, name, type, user)) {
                mProfilePetView.EditPetSuccessful();
            } else {
                mProfilePetView.EditPetError();
            }
        }
    }

    public List<Task> getAllPetTasks(Integer petId) {
        List<Task> taskList;

        taskList = mProfilePetModel.getAllPetTasks(petId);
        return taskList;
    }

    public void deletePet(Integer id) {
        mProfilePetView.deletePet(id);
    }
}