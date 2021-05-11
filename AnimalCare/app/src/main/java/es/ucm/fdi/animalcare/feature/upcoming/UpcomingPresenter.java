package es.ucm.fdi.animalcare.feature.upcoming;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        taskList = mUpcomingModel.getAllTasks(userId);
        return taskList;
    }

    public void addNewTask() { mUpcomingView.addNewTask(); }

    public Integer validateNewTask(String name, String desc, String petName, String date, int hour, int minute, User user) {

        Integer taskId = null;
        Date dateAux = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String datetime;

        try {
            dateAux = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateAux.parse(date);
        dateAux.setHours(hour);
        dateAux.setMinutes(minute);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        datetime = dateFormat.format(dateAux);

        if(name.length() == 0 && desc.length() == 0) mUpcomingView.fillFields();
        else {
            Integer petId = null;
            for(Pets p: user.getmPetList()){
                if (p.getName().equals(petName)){
                    petId = p.getId();
                    break;
                }
            }
            taskId = mUpcomingModel.saveNewTask(name, desc, datetime, petId);
        }

        return taskId;
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

    public Integer getPetPosition(User user, Integer petId){
        int petPosition = 0;

        List<Pets> petsList = user.getmPetList();
        int i = 0;
        for(Pets p : petsList) {
            if (p.getId() == petId){
                petPosition = i;
                break;
            }
            i++;
        }

        return petPosition;
    }

    public Integer removeTask(Integer taskId) {
        Integer taskReturn;

        taskReturn = mUpcomingModel.removeTask(taskId);

        return taskReturn;
    }

    public Integer validateUpdateTask(Integer taskId, String name, String desc, String petName, String date, int hour, int minute, User user) {

        Integer taskReturn = 0;
        Date dateAux = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String datetime;

        try {
            dateAux = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateAux.parse(date);
        dateAux.setHours(hour);
        dateAux.setMinutes(minute);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        datetime = dateFormat.format(dateAux);

        if(name.length() == 0 && desc.length() == 0) mUpcomingView.fillFields();
        else {
            Integer petId = null;
            for(Pets p: user.getmPetList()){
                if (p.getName().equals(petName)){
                    petId = p.getId();
                    break;
                }
            }
            taskReturn = mUpcomingModel.updateTask(taskId, name, desc, datetime, petId);
        }

        return taskReturn;
    }
}
