package es.ucm.fdi.animalcare.feature.newTask;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.data.User;

public class NewTaskActivity extends BaseActivity implements NewTaskView {
    public static final int NEW_TASK_SUCCESS = 1;
    public static final int NEW_TASK_FAIL = 2;
    public static final int EDIT_TASK_SUCCESS = 1;
    public static final int EDIT_TASK_FAIL = 2;


    private NewTaskPresenter mNewTaskPresenter;
    private User user;
    private Task task;
    private EditText mTaskName, mTaskDesc, mScheduleDate;
    private Spinner mPetSpinner, mFreqSpinner;
    private TimePicker mScheduleTime;
    private Button buttonConfirmTask;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        mNewTaskPresenter = new NewTaskPresenter(this);
        user = (User) getIntent().getSerializableExtra("user");
        task = (Task) getIntent().getSerializableExtra("task");

        mTaskName = findViewById(R.id.editTextTaskName);
        mTaskDesc = findViewById(R.id.editTextTaskDesc);
        mPetSpinner = findViewById(R.id.petSpinner);
        mFreqSpinner = findViewById(R.id.freqSpinner);
        mScheduleDate = findViewById(R.id.scheduleDate);
        mScheduleTime = findViewById(R.id.scheduleTime);
        buttonConfirmTask = findViewById(R.id.buttonConfirmTask);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if(task == null){
            Calendar calendar = Calendar.getInstance();
            String date = dateFormat.format(calendar.getTime());

            mScheduleDate.setText(date);
            mScheduleTime.setIs24HourView(true);
            mScheduleTime.setHour(calendar.getTime().getHours());
            mScheduleTime.setMinute(calendar.getTime().getMinutes());

            String[] options = mNewTaskPresenter.getPetNames(user);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
            mPetSpinner.setAdapter(adapter);

            options = Task.getmFreqNames();
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
            mFreqSpinner.setAdapter(adapter);

            buttonConfirmTask.setText(getResources().getString(R.string.new_task_button));
            buttonConfirmTask.setOnClickListener(v -> confirmNewTask(v) );
        } else {

            mTaskName.setText(task.getmTaskName());
            mTaskDesc.setText(task.getmDescription());
            mScheduleTime.setIs24HourView(true);
            mScheduleTime.setHour(task.getmScheduleDatetime().getHours());
            mScheduleTime.setMinute(task.getmScheduleDatetime().getMinutes());
            mScheduleDate.setText(dateFormat.format(task.getmScheduleDatetime()));

            buttonConfirmTask.setText("Modificar");
            buttonConfirmTask.setOnClickListener(v -> confirmUpdateTask(v));

            mPetSpinner = findViewById(R.id.petSpinner);
            String[] options = mNewTaskPresenter.getPetNames(user);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
            mPetSpinner.setAdapter(adapter);
            mPetSpinner.setSelection(mNewTaskPresenter.getPetPosition(user, task.getmPetId()));
            mPetSpinner.setEnabled(false);

            String[] options2 = Task.getmFreqNames();
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options2);
            mFreqSpinner.setAdapter(adapter2);
            mFreqSpinner.setSelection(task.getmFreq());
            mFreqSpinner.setEnabled(false);
        }
    }

    @Override
    public void showDatePickerDialog(View view) {
        NewTaskModel.DatePickerFragment newFragment = NewTaskModel.DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            final String selectedDate = day + "/" + (month+1) + "/" + year;
            mScheduleDate.setText(selectedDate);
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void confirmNewTask(View view){
        String name = mTaskName.getText().toString();
        String desc = mTaskDesc.getText().toString();
        String petName = mPetSpinner.getSelectedItem().toString();
        String date = mScheduleDate.getText().toString();
        Integer hour = mScheduleTime.getHour();
        Integer minutes = mScheduleTime.getMinute();
        Integer freq = mFreqSpinner.getSelectedItemPosition();
        Integer result = mNewTaskPresenter.validateNewTask(name, desc, petName, date, hour, minutes, user, freq);

        returnFromNewTask(result != -1 ? NEW_TASK_SUCCESS: NEW_TASK_FAIL);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void confirmUpdateTask(View view) {
        String name = mTaskName.getText().toString();
        String desc = mTaskDesc.getText().toString();
        String petName = mPetSpinner.getSelectedItem().toString();
        String date = mScheduleDate.getText().toString();
        Integer hour = mScheduleTime.getHour();
        Integer minutes = mScheduleTime.getMinute();
        Integer freq = mFreqSpinner.getSelectedItemPosition();
        Integer result = mNewTaskPresenter.validateUpdateTask(task.getmId(), name, desc, petName, date, hour, minutes, user, freq);
        returnFromNewTask(result != -1 ? EDIT_TASK_SUCCESS: EDIT_TASK_FAIL);
    }

    @Override
    public void fillFields() {
        Toast toast = Toast.makeText(this, getResources().getString(R.string.toast_fill_fields), Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void returnFromNewTask(int result) {
        setResult(result);
        finish();
    }

    public void goBack (View view){
        finish();
    }
}
