package es.ucm.fdi.animalcare.feature.upcoming;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.feature.newTask.NewTaskActivity;
import es.ucm.fdi.animalcare.feature.showTask.ShowTaskActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;

public class UpcomingActivity extends ToolBarManagement implements UpcomingView {
    public static final int NEW_TASK = 1;

    private UpcomingPresenter mUpcomingPresenter;
    private RecyclerView mTaskListView;
    private Button mAddTaskButton;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        setUpToolbar();

        mUpcomingPresenter = new UpcomingPresenter(this);

        mAddTaskButton = findViewById(R.id.addTaskButton);

        mAddTaskButton.setText(App.getApp().getResources().getString(R.string.new_task_button));

        mAddTaskButton.setOnClickListener(v -> {
            if (mUpcomingPresenter.hasPets())
                mUpcomingPresenter.addNewTask();
            else
                noPets();
        });
        updateList();
    }

    @Override
    public void  updateList(){
        taskList = new ArrayList<>();
        taskList = mUpcomingPresenter.getAllTasks(App.getApp().getUser().getmId());

        mTaskListView = findViewById(R.id.taskListView);
        mTaskListView.setLayoutManager(new LinearLayoutManager(this));
        mTaskListView.setHasFixedSize(true);
        taskAdapter = new TaskAdapter( taskList, this);
        mTaskListView.setAdapter(taskAdapter);
    }

    @Override
    public void noPets() {
        Toast toast = Toast.makeText(this, R.string.noPets, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void addNewTask(){
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivityForResult(intent, NEW_TASK);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showTask(Integer taskId) {
        Intent intent = new Intent(this, ShowTaskActivity.class);
        intent.putExtra("taskId", taskId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String text = "";
        if(requestCode == NEW_TASK){
            if(resultCode > 0)
                text = getResources().getString(R.string.toast_task_saved);
            else
                text = getResources().getString(R.string.toast_error);
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
            toast.show();
        }
        updateList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.recreate();
    }

    public String getPetName(Integer petId) {
        return mUpcomingPresenter.getPetName(petId);
    }

    @Override
    public void bindViews() {

    }

    public void setUpToolbar() {
        super.setUpToolbar(R.id.button_toolbar_upcoming);
    }
}