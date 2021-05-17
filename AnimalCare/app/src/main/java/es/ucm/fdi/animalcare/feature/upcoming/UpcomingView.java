package es.ucm.fdi.animalcare.feature.upcoming;

import es.ucm.fdi.animalcare.base.BaseView;

public interface UpcomingView extends BaseView {
    void addNewTask();
    void showTask(Integer taskId);
    void updateList();
    void noPets();
}
