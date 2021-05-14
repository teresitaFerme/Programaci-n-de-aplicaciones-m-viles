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
    public String getPetName(Integer petId) {
        return mUpcomingModel.getPetName(petId);
    }

    public List<Task> getAllTasks(Integer userId) {
        List<Task> taskList;

        taskList = mUpcomingModel.getAllTasks(userId);
        return taskList;
    }

    public void addNewTask() { mUpcomingView.addNewTask(); }
}
