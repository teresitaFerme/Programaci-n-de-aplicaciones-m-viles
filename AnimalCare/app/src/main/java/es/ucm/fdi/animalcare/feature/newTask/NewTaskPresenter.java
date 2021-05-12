package es.ucm.fdi.animalcare.feature.newTask;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import es.ucm.fdi.animalcare.base.BasePresenter;
import es.ucm.fdi.animalcare.data.Pets;
import es.ucm.fdi.animalcare.data.User;

public class NewTaskPresenter extends BasePresenter {
    private NewTaskView mNewTaskView;
    private NewTaskModel mNewTaskModel;

    public NewTaskPresenter(NewTaskView view) {
        this.mNewTaskView = view;
        this.mNewTaskModel = new NewTaskModel((Context) mNewTaskView);
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

    public int validateNewTask(String name, String desc, String petName, String date, int hour, int minute, User user) {
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

        if(name.length() == 0 && desc.length() == 0) mNewTaskView.fillFields();
        else {
            Integer petId = null;
            for(Pets p: user.getmPetList()){
                if (p.getName().equals(petName)){
                    petId = p.getId();
                    break;
                }
            }
            taskId = mNewTaskModel.saveNewTask(name, desc, datetime, petId);
        }

        return taskId;
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

    public Integer validateUpdateTask(int taskId, String name, String desc, String petName, String date, int hour, int minute, User user) {
        Date dateAux = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String datetime;
        Integer result = null;

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

        if(name.length() == 0 && desc.length() == 0) mNewTaskView.fillFields();
        else {
            Integer petId = null;
            for(Pets p: user.getmPetList()){
                if (p.getName().equals(petName)){
                    petId = p.getId();
                    break;
                }
            }
            result = mNewTaskModel.saveUpdateTask(taskId, name, desc, datetime, petId);
        }

        return result;
    }
}
