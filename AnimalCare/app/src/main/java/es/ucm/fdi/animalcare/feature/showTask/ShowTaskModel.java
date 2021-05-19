package es.ucm.fdi.animalcare.feature.showTask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.PetTable;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.TaskTable;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class ShowTaskModel {
    AnimalCareDbHelper dbHelper;
    Context ctx;

    public ShowTaskModel(Context ctx) {
        dbHelper = new AnimalCareDbHelper(ctx);
        this.ctx = ctx;
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

    public Integer removeTask(Integer taskId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(TaskTable.TABLE_NAME, BaseColumns._ID + "=?", new String[]{String.valueOf(taskId)});
    }

    public String getPetName(Integer petId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String petName = null;
        String[] projection = {
                BaseColumns._ID,
                PetTable.COLUMN_NAME_PETNAME
        };

        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {String.valueOf(petId)};

        Cursor cursor = db.query(
                PetTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToFirst())
            petName = cursor.getString(cursor.getColumnIndex(PetTable.COLUMN_NAME_PETNAME));
        cursor.close();

        return petName;
    }

    public Integer changeTaskState(Integer taskId) {
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();
        Integer result = 0;
        String[] projection = {
                BaseColumns._ID,
                TaskTable.COLUMN_NAME_TASKDONE_DATETIME
        };


        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {String.valueOf(taskId)};

        Cursor cursor = dbRead.query(
                TaskTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToFirst()){
            String date = null;
            ContentValues values = new ContentValues();
            String datetime = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_TASKDONE_DATETIME));
            if(datetime == null){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Calendar calendar = Calendar.getInstance();
                date = dateFormat.format(calendar.getTime());
            }
            values.put(TaskTable.COLUMN_NAME_TASKDONE_DATETIME, date);
            result = dbWrite.update(TaskTable.TABLE_NAME, values, BaseColumns._ID + "=?", new String[]{String.valueOf(taskId)});
        }
        cursor.close();

        return result;
    }

}
