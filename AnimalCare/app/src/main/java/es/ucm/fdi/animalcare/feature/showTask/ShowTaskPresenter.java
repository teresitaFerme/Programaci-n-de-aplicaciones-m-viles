package es.ucm.fdi.animalcare.feature.showTask;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.ucm.fdi.animalcare.base.BasePresenter;
import es.ucm.fdi.animalcare.data.Pets;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.data.User;

public class ShowTaskPresenter extends BasePresenter {
    private ShowTaskView mShowTaskView;
    private ShowTaskModel mShowTaskModel;

    public ShowTaskPresenter(ShowTaskView view) {
        this.mShowTaskView = view;
        this.mShowTaskModel = new ShowTaskModel((Context) mShowTaskView);
    }

    public Task getTaskById(Integer taskId) {
        return mShowTaskModel.getTaskById(taskId);
    }

    public Integer removeTask(Integer taskId) {
        Integer taskReturn;

        taskReturn = mShowTaskModel.removeTask(taskId);

        return taskReturn;
    }

    /*public Integer validateUpdateTask(Integer taskId, String name, String desc, String petName, String date, int hour, int minute, User user) {

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

        if(name.length() == 0 && desc.length() == 0) mShowTaskView.fillFields();
        else {
            Integer petId = null;
            for(Pets p: user.getmPetList()){
                if (p.getName().equals(petName)){
                    petId = p.getId();
                    break;
                }
            }
            taskReturn = mShowTaskModel.updateTask(taskId, name, desc, datetime, petId);
        }

        return taskReturn;
    }*/
}
