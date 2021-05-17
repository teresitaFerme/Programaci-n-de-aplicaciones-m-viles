package es.ucm.fdi.animalcare.feature.upcoming;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.newTask.NewTaskActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.showTask.ShowTaskActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class UpcomingActivity extends BaseActivity implements UpcomingView, ToolBarManagement {
    public static final int NEW_TASK = 1;

    private UpcomingPresenter mUpcomingPresenter;
    private User user;
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

        mAddTaskButton.setOnClickListener(v -> {
            if (mUpcomingPresenter.hasPets())
                mUpcomingPresenter.addNewTask();
            else
                noPets();
        });

        user = (User) getIntent().getSerializableExtra("user");
        updateList();
    }

    @Override
    public void launchFromToolbar(View view) {
        if(view.getId() != (R.id.button_toolbar_upcoming)){
            Intent intent;
            switch (view.getId()){
                case R.id.button_toolbar_pets:
                    intent = new Intent(this, PetsActivity.class);
                    break;
                case R.id.button_toolbar_user:
                    intent = new Intent(this, UserActivity.class);
                    break;
                case R.id.button_toolbar_calendar:
                    intent = new Intent(this, CalendarActivity.class);
                    break;
                default:
                    intent = new Intent(this, SettingsActivity.class);
                    break;
            }
            intent.putExtra("user", user);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void  updateList(){
        taskList = new ArrayList<>();
        taskList = mUpcomingPresenter.getAllTasks(user.getmId());

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
        intent.putExtra("user", user);
        startActivityForResult(intent, NEW_TASK);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showTask(Integer taskId) {
        Intent intent = new Intent(this, ShowTaskActivity.class);
        intent.putExtra("user", user);
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

    @Override
    public void setUpToolbar() {
        findViewById(R.id.button_toolbar_pets).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(App.getApp().getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
    }
}