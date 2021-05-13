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

import java.util.Calendar;

import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.TaskTable;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class NewTaskModel extends BaseModel {
    AnimalCareDbHelper dbHelper;

    public NewTaskModel(Context ctx){dbHelper = new AnimalCareDbHelper(ctx);}

    public Integer saveNewTask(String name, String desc, String datetime, Integer petId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_NAME_TASKNAME, name);
        values.put(TaskTable.COLUMN_NAME_DESCRIPTION, desc);
        values.put(TaskTable.COLUMN_NAME_SCHEDULE_DATETIME, datetime);
        values.put(TaskTable.COLUMN_NAME_ID_PET, petId);

        long newRowId = db.insert(TaskTable.TABLE_NAME, null, values);

        return (int) newRowId;
    }

    public Integer saveUpdateTask(int taskId, String name, String desc, String datetime, Integer petId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_NAME_TASKNAME, name);
        values.put(TaskTable.COLUMN_NAME_DESCRIPTION, desc);
        values.put(TaskTable.COLUMN_NAME_SCHEDULE_DATETIME, datetime);
        values.put(TaskTable.COLUMN_NAME_ID_PET, petId);

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
