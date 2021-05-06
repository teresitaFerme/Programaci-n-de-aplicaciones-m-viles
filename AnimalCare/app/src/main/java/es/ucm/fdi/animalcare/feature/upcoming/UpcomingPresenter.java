package es.ucm.fdi.animalcare.feature.upcoming;

import android.content.Context;

import java.util.List;

import es.ucm.fdi.animalcare.base.BasePresenter;
import es.ucm.fdi.animalcare.data.Pets;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.data.User;

public class UpcomingPresenter extends BasePresenter {
    private UpcomingView mUpcomingView;
    private UpcomingModel mUpcomingModel;

    UpcomingPresenter(UpcomingView view){
        mUpcomingView = view;
        mUpcomingModel = new UpcomingModel((Context) view);
    }

    public List<Task> getAllTasks(Integer userId) {
        List<Task> taskList;
        /*List<Pets> petsList;

        petsList = mTasksModel.getPets(userId);
        Integer[] petIds = new Integer[petsList.size()];

        for(int i = 0; i < petsList.size(); i++){ petIds[i] = petsList.get(i).getId() ;}
        taskList = mTasksModel.getAllTasks(petIds);
        */
        taskList = mUpcomingModel.getAllTasks(userId);
        return taskList;
    }

    public void addNewTask() { mUpcomingView.addNewTask(); }

    public void validateNewTask(String name, String desc, String petName, String date, User user) {
        if(name.length() == 0 && desc.length() == 0) mUpcomingView.fillFields();
        else {
            Integer petId = null;
            for(Pets p: user.getmPetList()){
                if (p.getName().equals(petName)){
                    petId = p.getId();
                    break;
                }
            }
            mUpcomingModel.saveNewTask(name, desc, date, petId);
            mUpcomingView.newTaskSuccessful();
        }
    }

    public String[] getPetNames(User user) {
        List<Pets> petsList = user.getmPetList();

        String[] petNames = new String[petsList.size()];
        int i = 0;
        for(Pets p : petsList){
            petNames[i] = p.getName();
            i++;
        }

        return petNames;
    }


}
