package es.ucm.fdi.animalcare.feature.upcoming;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.PetTable;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.TaskTable;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class UpcomingModel {
    AnimalCareDbHelper dbHelper;
    Context ctx;

    UpcomingModel(Context ctx) {
        dbHelper = new AnimalCareDbHelper(ctx);
        this.ctx = ctx;
    }

    public List<Task> getAllTasks (Integer userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT t." + BaseColumns._ID +
                ", t." + TaskTable.COLUMN_NAME_ID_PET +
                ", t." + TaskTable.COLUMN_NAME_TASKNAME +
                ", t." + TaskTable.COLUMN_NAME_SCHEDULE_DATETIME +
                ", t." + TaskTable.COLUMN_NAME_TASKDONE_DATETIME +
                ", t." + TaskTable.COLUMN_NAME_DESCRIPTION +
                ", t." + TaskTable.COLUMN_NAME_FREQUENCY +
                " FROM " + TaskTable.TABLE_NAME + " t JOIN " + PetTable.TABLE_NAME +
                " p ON t." + TaskTable.COLUMN_NAME_ID_PET + " = p." + BaseColumns._ID + " WHERE p." + PetTable.COLUMN_NAME_ID_OWNER +
                "= " + String.valueOf(userId) + " ORDER BY t." + TaskTable.COLUMN_NAME_SCHEDULE_DATETIME;
        Cursor cursor = db.rawQuery(query, null);
        List values = new ArrayList<Task>();

        cursor.moveToLast();
        while(cursor.moveToPrevious()){
            Integer taskId = cursor.getInt(cursor.getColumnIndex(TaskTable._ID));
            Integer petIdAux = cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_NAME_ID_PET));
            String taskName = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_TASKNAME));
            String scheduleDatetime = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_SCHEDULE_DATETIME));
            String taskDoneDatetime = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_TASKDONE_DATETIME));
            String description = cursor.getString(cursor.getColumnIndex(TaskTable.COLUMN_NAME_DESCRIPTION));
            Integer freq = cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_NAME_FREQUENCY));
            Task task = new Task(taskId, petIdAux, taskName, scheduleDatetime, taskDoneDatetime, description, freq, ctx.getResources().getStringArray(R.array.task_frequency_array));
            values.add(task);
        }
        cursor.close();

        return values;
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

    public boolean hasPets() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT count(*) FROM " + PetTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        return count > 0;
    }
}
