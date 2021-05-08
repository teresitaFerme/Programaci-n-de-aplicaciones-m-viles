package es.ucm.fdi.animalcare.feature.upcoming;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.PetTable;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.TaskTable;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class UpcomingModel {
    AnimalCareDbHelper dbHelper;

    String[] projection = {
            BaseColumns._ID,
            TaskTable.COLUMN_NAME_ID_PET,
            TaskTable.COLUMN_NAME_TASKNAME,
            TaskTable.COLUMN_NAME_SCHEDULE_DATETIME,
            TaskTable.COLUMN_NAME_TASKDONE_DATETIME,
            TaskTable.COLUMN_NAME_DESCRIPTION,
            TaskTable.COLUMN_NAME_FREQUENCY
    };

    UpcomingModel(Context ctx) { dbHelper = new AnimalCareDbHelper(ctx); }

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

    public List<Task> getAllTasks (Integer userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT t." + BaseColumns._ID +
                ", t." + TaskTable.COLUMN_NAME_ID_PET +
                ", t." + TaskTable.COLUMN_NAME_TASKNAME +
                ", t." + TaskTable.COLUMN_NAME_SCHEDULE_DATETIME +
                ", t." + TaskTable.COLUMN_NAME_DESCRIPTION +
                " FROM " + TaskTable.TABLE_NAME + " t JOIN " + PetTable.TABLE_NAME +
                " p ON t." + TaskTable.COLUMN_NAME_ID_PET + " = p." + BaseColumns._ID + " WHERE p." + PetTable.COLUMN_NAME_ID_OWNER +
                "= " + String.valueOf(userId) + " ORDER BY t." + TaskTable.COLUMN_NAME_SCHEDULE_DATETIME;
        Cursor cursor = db.rawQuery(query, null);
        List values = new ArrayList<Task>();

        while(cursor.moveToNext()){

            //Date taskDoneDatetime;
            Integer taskId = cursor.getInt(cursor.getColumnIndex(TaskTable._ID));
            Integer petIdAux = cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_NAME_ID_PET));
            String taskName = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_TASKNAME));
            String scheduleDatetime = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_SCHEDULE_DATETIME));
            String description = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_DESCRIPTION));
            //Task.Frequency frequency = Task.Frequency.values()[cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_NAME_FREQUENCY))];
            Task task = new Task(taskId, petIdAux, taskName, scheduleDatetime, description);
            values.add(task);
        }
        cursor.close();

        return values;
    }

    public Integer removeTask(Integer taskId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(TaskTable.TABLE_NAME, BaseColumns._ID + "=?", new String[]{String.valueOf(taskId)});
    }

    public Integer updateTask(Integer taskId, String name, String desc, String datetime, Integer petId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskTable.COLUMN_NAME_TASKNAME, name);
        values.put(TaskTable.COLUMN_NAME_DESCRIPTION, desc);
        values.put(TaskTable.COLUMN_NAME_SCHEDULE_DATETIME, datetime);
        values.put(TaskTable.COLUMN_NAME_ID_PET, petId);

        return db.update(TaskTable.TABLE_NAME, values, BaseColumns._ID + "=?", new String[]{String.valueOf(taskId)});
    }
}
