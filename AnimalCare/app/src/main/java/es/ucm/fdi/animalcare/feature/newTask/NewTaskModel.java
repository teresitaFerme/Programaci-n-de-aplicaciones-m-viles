package es.ucm.fdi.animalcare.feature.newTask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.TaskTable;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class NewTaskModel extends BaseModel {
    AnimalCareDbHelper dbHelper;

    public NewTaskModel(Context ctx){dbHelper = new AnimalCareDbHelper(ctx);}

    public Integer saveNewTask(String name, String desc, String datetime, Integer petId, Integer freq) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Calendar calendar;
        int interval=1, loopLimit=1; // Interval: number of days/weeks/months/years to add to original date

        // Check frequency and insert necessary number of copies of task within a year into database
        switch(freq){
            case Task.FREQUENCY_DAILY:
                loopLimit = 365;
                break;
            case Task.FREQUENCY_WEEKLY:
                loopLimit = 52;
                break;
            case Task.FREQUENCY_MONTHLY:
                loopLimit = 12;
                break;
            case Task.FREQUENCY_YEARLY:
                loopLimit = 2;
                break;
            default: // No frequency
                interval = 0;
        }

        // First iteration
        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_NAME_TASKNAME, name);
        values.put(TaskTable.COLUMN_NAME_DESCRIPTION, desc);
        values.put(TaskTable.COLUMN_NAME_SCHEDULE_DATETIME, datetime);
        values.put(TaskTable.COLUMN_NAME_ID_PET, petId);
        values.put(TaskTable.COLUMN_NAME_FREQUENCY, freq);
        long newRowId = db.insert(TaskTable.TABLE_NAME, null, values);

        calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(datetime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < loopLimit; i++){
            switch(freq){
                case Task.FREQUENCY_DAILY:
                    calendar.add(Calendar.DATE, interval);
                    break;
                case Task.FREQUENCY_WEEKLY:
                    calendar.add(Calendar.WEEK_OF_YEAR, interval);
                    break;
                case Task.FREQUENCY_MONTHLY:
                    calendar.add(Calendar.MONTH, interval);
                    break;
                case Task.FREQUENCY_YEARLY:
                    calendar.add(Calendar.YEAR, interval);
                    break;
                default: // No frequency: for loop not reachable for this case
            }

            String dateTimeString = dateFormat.format(calendar.getTime());
            values = new ContentValues();
            values.put(TaskTable.COLUMN_NAME_TASKNAME, name);
            values.put(TaskTable.COLUMN_NAME_DESCRIPTION, desc);
            values.put(TaskTable.COLUMN_NAME_SCHEDULE_DATETIME, dateTimeString);
            values.put(TaskTable.COLUMN_NAME_ID_PET, petId);
            values.put(TaskTable.COLUMN_NAME_FREQUENCY, freq);
            db.insert(TaskTable.TABLE_NAME, null, values);
        }

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
}
