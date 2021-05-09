package es.ucm.fdi.animalcare.feature.upcoming;

import es.ucm.fdi.animalcare.base.BaseView;

public interface UpcomingView extends BaseView {
    void addNewTask();
    void fillFields();
    void newTaskSuccessful();
    void cancelNewTask();
}
