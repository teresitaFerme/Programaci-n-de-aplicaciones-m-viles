package es.ucm.fdi.animalcare.feature.showTask;

import android.view.View;

public interface ShowTaskView {
    void removeTask(View view);
    void removeTaskSuccess();
    void removeTaskFail();
    void editTask(View view);
    void changeTaskState(View view);
    void changeTaskStateSuccess();
    void changeTaskStateFail();
}
