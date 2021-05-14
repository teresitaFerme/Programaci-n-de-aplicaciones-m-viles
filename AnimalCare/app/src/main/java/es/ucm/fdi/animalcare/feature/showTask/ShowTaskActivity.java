package es.ucm.fdi.animalcare.feature.showTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button mChangeTaskStateButton;
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
        mChangeTaskStateButton = findViewById(R.id.checkButton);

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

        if(task.getmTaskDoneDatetime() != null)
            mChangeTaskStateButton.setText(getResources().getString(R.string.undone_task_button));
    }

    @Override
    public void removeTask(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(getResources().getString(R.string.remove_task_dialog));
        alertDialog.setPositiveButton(getResources().getString(R.string.dialog_yes), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                mShowTaskPresenter.removeTask(task.getmId());
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.dialog_no), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing
            }
        });
        alertDialog.show();
    }

    @Override
    public void removeTaskSuccess() {
        Toast toast = Toast.makeText(ShowTaskActivity.this, getResources().getString(R.string.toast_task_removed), Toast.LENGTH_LONG);
        toast.show();
        finish();
    }

    @Override
    public void removeTaskFail() {
        Toast toast = Toast.makeText(ShowTaskActivity.this, getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void editTask(View view) {
        Intent intent = new Intent(this, NewTaskActivity.class);
        intent.putExtra("task", task);
        intent.putExtra("user", user);
        startActivityForResult(intent, EDIT_TASK);
    }

    @Override
    public void changeTaskState(View view) {
        mShowTaskPresenter.changeTaskState(task.getmId());
    }

    @Override
    public void changeTaskStateSuccess() {
        Resources res = getResources();
        Toast toast = Toast.makeText(ShowTaskActivity.this, res.getString(R.string.toast_task_edited) , Toast.LENGTH_LONG);
        String str = res.getString(R.string.done_task_button);
        if(mChangeTaskStateButton.getText().toString().equals(str))
            str = res.getString(R.string.undone_task_button);
        mChangeTaskStateButton.setText(str);
        toast.show();
    }

    @Override
    public void changeTaskStateFail() {
        Toast toast = Toast.makeText(ShowTaskActivity.this, getResources().getString(R.string.toast_error), Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String text = "";
        if (requestCode == EDIT_TASK) {
            if (resultCode == NewTaskActivity.EDIT_TASK_SUCCESS) {
                text = getResources().getString(R.string.toast_task_edited);
            }
            else
                text = getResources().getString(R.string.toast_error);
            Toast toast =  Toast.makeText(ShowTaskActivity.this, text, Toast.LENGTH_LONG);
            toast.show();
            finish();
            startActivity(getIntent());
        }
    }

    public void goBack (View view){
        finish();
    }
}