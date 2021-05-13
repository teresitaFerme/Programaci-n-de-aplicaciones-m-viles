package es.ucm.fdi.animalcare.feature.showTask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class ShowTaskModel {
    AnimalCareDbHelper dbHelper;

    public ShowTaskModel(Context ctx) { dbHelper = new AnimalCareDbHelper(ctx);
    }

    public Task getTaskById(Integer taskId) {
        Task task = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET,
                AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME,
                AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME,
                //AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKDONE_DATETIME,
                AnimalCareDatabase.TaskTable.COLUMN_NAME_DESCRIPTION
                //AnimalCareDatabase.TaskTable.COLUMN_NAME_FREQUENCY
        };


        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskId)};

        Cursor cursor = db.query(
                AnimalCareDatabase.TaskTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToFirst()){
            Integer petIdAux = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET));
            String taskName = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME));
            String scheduleDatetime = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME));
            String description = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_DESCRIPTION));
            //Task.Frequency frequency = Task.Frequency.values()[cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_NAME_FREQUENCY))];
            task = new Task(taskId, petIdAux, taskName, scheduleDatetime, description);
        }
        cursor.close();

        return task;
    }

    public Integer removeTask(Integer taskId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(AnimalCareDatabase.TaskTable.TABLE_NAME, BaseColumns._ID + "=?", new String[]{String.valueOf(taskId)});
    }

    public Integer updateTask(Integer taskId, String name, String desc, String datetime, Integer petId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME, name);
        values.put(AnimalCareDatabase.TaskTable.COLUMN_NAME_DESCRIPTION, desc);
        values.put(AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME, datetime);
        values.put(AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET, petId);

        return db.update(AnimalCareDatabase.TaskTable.TABLE_NAME, values, BaseColumns._ID + "=?", new String[]{String.valueOf(taskId)});
    }
}
