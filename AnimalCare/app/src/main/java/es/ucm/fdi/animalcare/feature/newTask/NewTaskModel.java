package es.ucm.fdi.animalcare.feature.newTask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.TaskTable;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class NewTaskModel extends BaseModel {
    AnimalCareDbHelper dbHelper;
    Context ctx;

    public NewTaskModel(Context ctx){
        this.ctx = ctx;
        dbHelper = new AnimalCareDbHelper(ctx);
    }

    public Integer saveNewTask(String name, String desc, String datetime, Integer petId, Integer freq) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insertion
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_NAME_TASKNAME, name);
        values.put(TaskTable.COLUMN_NAME_DESCRIPTION, desc);
        values.put(TaskTable.COLUMN_NAME_SCHEDULE_DATETIME, datetime);
        values.put(TaskTable.COLUMN_NAME_ID_PET, petId);
        values.put(TaskTable.COLUMN_NAME_FREQUENCY, freq);
        long newRowId = db.insert(TaskTable.TABLE_NAME, null, values);

        return (int) newRowId;
    }

    public Integer saveUpdateTask(int taskId, String name, String desc, String datetime, Integer petId, Integer freq) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_NAME_TASKNAME, name);
        values.put(TaskTable.COLUMN_NAME_DESCRIPTION, desc);
        values.put(TaskTable.COLUMN_NAME_SCHEDULE_DATETIME, datetime);
        values.put(TaskTable.COLUMN_NAME_ID_PET, petId);
        values.put(TaskTable.COLUMN_NAME_FREQUENCY, freq);

        long result = db.update(TaskTable.TABLE_NAME, values, BaseColumns._ID + "=?", new String[]{String.valueOf(taskId)});

        return (int) result;
    }

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }

    public Task getTaskById(Integer taskId) {
        Task task = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                TaskTable.COLUMN_NAME_ID_PET,
                TaskTable.COLUMN_NAME_TASKNAME,
                TaskTable.COLUMN_NAME_SCHEDULE_DATETIME,
                TaskTable.COLUMN_NAME_TASKDONE_DATETIME,
                TaskTable.COLUMN_NAME_DESCRIPTION,
                TaskTable.COLUMN_NAME_FREQUENCY
        };

        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskId)};

        Cursor cursor = db.query(
                TaskTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToFirst()){
            Integer petIdAux = cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_NAME_ID_PET));
            String taskName = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_TASKNAME));
            String scheduleDatetime = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_SCHEDULE_DATETIME));
            String taskDoneDatetime = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_TASKDONE_DATETIME));
            String description = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_DESCRIPTION));
            Integer frequency = cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_NAME_FREQUENCY));
            task = new Task(taskId, petIdAux, taskName, scheduleDatetime, taskDoneDatetime, description, frequency, ctx.getResources().getStringArray(R.array.task_frequency_array));
        }
        cursor.close();

        return task;
    }
}