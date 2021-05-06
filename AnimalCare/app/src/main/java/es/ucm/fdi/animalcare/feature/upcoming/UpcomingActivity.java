package es.ucm.fdi.animalcare.feature.upcoming;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.calendar.CalendarActivity;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class UpcomingActivity extends BaseActivity implements UpcomingView, ToolBarManagement {
    private UpcomingPresenter mUpcomingPresenter;
    private User user;
    private RecyclerView mTaskListView;
    private Button mAddTaskButton;
    private Button mAddTaskConfirm;
    private EditText mTaskName;
    private Spinner mPetSpinner;
    private EditText mScheduleDate;
    private TimePicker mScheduleTime;
    private EditText mTaskDesc;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        findViewById(R.id.button_toolbar_pets).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(getResources().getColor(R.color.iconColor));

        mTaskListView = findViewById(R.id.taskListView);
        mAddTaskButton = findViewById(R.id.addTaskButton);
        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mUpcomingPresenter.addNewTask(); }
        });

        mUpcomingPresenter = new UpcomingPresenter(this);
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
            startActivity(intent);
        }
    }
    @Override
    public void fillFields() {
        Toast toast = Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void newTaskSuccessful() {
        setContentView(R.layout.activity_upcoming);
        Toast toast = Toast.makeText(this, "Tarea Guardada", Toast.LENGTH_LONG);
        toast.show();
        updateList();
    }

    public void  updateList(){
        taskList = new ArrayList<>();
        taskList = mUpcomingPresenter.getAllTasks(user.getmId());

        mTaskListView.setLayoutManager(new LinearLayoutManager(this));
        mTaskListView.setHasFixedSize(true);
        taskAdapter = new TaskAdapter( taskList, this);
        mTaskListView.setAdapter(taskAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addNewTask(){
        setContentView(R.layout.activity_new_task);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        String date = dateFormat.format(calendar.getTime());

        mUpcomingPresenter = new UpcomingPresenter(this);

        mTaskName = findViewById(R.id.editTextTaskName);
        mTaskDesc = findViewById(R.id.editTextTaskDesc);
        mAddTaskConfirm = findViewById(R.id.buttonConfirmNewTask);
        mPetSpinner = findViewById(R.id.petSpinner);
        mScheduleDate = findViewById(R.id.scheduleDate);
        mScheduleDate.setText(date);
        mScheduleTime = findViewById(R.id.scheduleTime);
        mScheduleTime.setIs24HourView(true);
        mScheduleTime.setHour(calendar.getTime().getHours());
        mScheduleTime.setMinute(calendar.getTime().getMinutes());

        String[] options = mUpcomingPresenter.getPetNames(user);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        mPetSpinner.setAdapter(adapter);

        mScheduleDate.setOnClickListener(view -> {
            switch (view.getId()) {
                case R.id.scheduleDate:
                    showDatePickerDialog();
                    break;
            }
        });

        mAddTaskConfirm.setOnClickListener(v -> {
            String datetime = mScheduleDate.getText().toString()
                    .concat(" " + String.valueOf(mScheduleTime.getHour()) + ":" + String.valueOf(mScheduleTime.getMinute()));

            mUpcomingPresenter.validateNewTask(
                    mTaskName.getText().toString(), mTaskDesc.getText().toString(),
                    mPetSpinner.getSelectedItem().toString(), datetime, user);

            finish();
            startActivity(getIntent());
        }
        );
    }

    private void showDatePickerDialog() {
            DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                mScheduleDate.setText(selectedDate);
            });

            newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}