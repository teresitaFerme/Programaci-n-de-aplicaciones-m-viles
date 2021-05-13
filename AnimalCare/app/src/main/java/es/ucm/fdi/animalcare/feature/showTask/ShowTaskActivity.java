package es.ucm.fdi.animalcare.feature.showTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.newTask.NewTaskActivity;

public class ShowTaskActivity extends BaseActivity implements ShowTaskView {
    public static final int EDIT_TASK = 1;

    private ShowTaskPresenter mShowTaskPresenter;

    private TextView mTaskLabel, mTaskTime, mTaskDate, mTaskDesc, mTaskFreq, mTaskPetName;
    private User user;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"), dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mShowTaskPresenter = new ShowTaskPresenter(this);

        mTaskLabel = findViewById(R.id.taskLabel);
        mTaskTime = findViewById(R.id.taskTime);
        mTaskDate = findViewById(R.id.taskDate);
        mTaskDesc = findViewById(R.id.taskDesc);
        mTaskFreq = findViewById(R.id.taskFreq);
        mTaskPetName = findViewById(R.id.taskPetName);

        Integer taskId = getIntent().getIntExtra("taskId", 0);
        task = mShowTaskPresenter.getTaskById(taskId);
        user = (User) getIntent().getSerializableExtra("user");
        String petName = mShowTaskPresenter.getPetName(task.getmPetId());

        mTaskLabel.setText(task.getmTaskName());
        mTaskTime.setText(timeFormat.format(task.getmScheduleDatetime()));
        mTaskDate.setText(dateFormat.format(task.getmScheduleDatetime()));
        mTaskDesc.setText(task.getmDescription());
        mTaskPetName.setText(petName);
        if (task.getmFreq() != Task.FREQUENCY_NONE)
            mTaskFreq.setText(task.getmFreqName());
    }

    @Override
    public void removeTask(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure you want to delete this task?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                if(mShowTaskPresenter.removeTask(task.getmId()) < 0) {
                    Toast toast = Toast.makeText(ShowTaskActivity.this, "Ha habido un error.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(ShowTaskActivity.this, "Tarea eliminado correctamente.", Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing
            }
        });
        alertDialog.show();
    }

    @Override
    public void editTask(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        intent.putExtra("task", task);
        intent.putExtra("user", user);
        startActivityForResult(intent, EDIT_TASK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String text = "";
        if (requestCode == EDIT_TASK) {
            if (resultCode == NewTaskActivity.EDIT_TASK_SUCCESS) {
                text = "Tarea Modificada.";
            }
            else
                text = "Ha habido un error.";
            Toast toast =  Toast.makeText(ShowTaskActivity.this, text, Toast.LENGTH_LONG);
            toast.show();
            finish();
            startActivity(getIntent());
        }
    }
}
