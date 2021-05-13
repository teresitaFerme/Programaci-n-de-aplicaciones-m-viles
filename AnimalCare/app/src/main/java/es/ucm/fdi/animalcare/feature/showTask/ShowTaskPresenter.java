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

    public String getPetName(Integer petId) {
        return mShowTaskModel.getPetName(petId);
    }
}
