package es.ucm.fdi.animalcare.feature.pets.profilePet;

public interface ProfilePetView {
    void editPet(Integer id);
    void EditPetSuccessful();
    void DeletePetSuccessful();
    void DeletePetError();
    void EditPetError();
    void fillField();
    void deletePet(Integer id);
}
