package es.ucm.fdi.animalcare.feature.pets.profilePet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class ProfilePetModel extends BaseModel {
    AnimalCareDbHelper dbHelper;

    ProfilePetModel(Context ctx) {
        dbHelper = new AnimalCareDbHelper(ctx);
    }

    public boolean deletePet(Integer petId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.delete(AnimalCareDatabase.PetTable.TABLE_NAME, BaseColumns._ID + "=?", new String[]{String.valueOf(petId)});

        if (newRowId == -1) return false;
        else return true;
    }

    public boolean editPet(Integer petId, String name, String type, Integer userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AnimalCareDatabase.PetTable.COLUMN_NAME_PETNAME, name);
        values.put(AnimalCareDatabase.PetTable.COLUMN_NAME_TYPE, type);
        values.put(AnimalCareDatabase.PetTable.COLUMN_NAME_ID_OWNER, userId);

        long newRowId = db.update(AnimalCareDatabase.PetTable.TABLE_NAME, values, BaseColumns._ID + "=?", new String[]{String.valueOf(petId)});

        if (newRowId == -1) return false;
        else return true;
    }

    public List<Task> getAllPetTasks(Integer petId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT t." + BaseColumns._ID +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_DESCRIPTION +
                " FROM " + AnimalCareDatabase.TaskTable.TABLE_NAME + " t JOIN " + AnimalCareDatabase.PetTable.TABLE_NAME +
                " p ON t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET + " = p." + BaseColumns._ID + " WHERE p." + AnimalCareDatabase.PetTable._ID +
                "= " + String.valueOf(petId) + " ORDER BY t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME;
        Cursor cursor = db.rawQuery(query, null);
        List values = new ArrayList<Task>();

        while(cursor.moveToNext()){

            //Date taskDoneDatetime;
            Integer taskId = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable._ID));
            Integer petIdAux = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET));
            String taskName = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME));
            String scheduleDatetime = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME));
            String description = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_DESCRIPTION));
            //Task.Frequency frequency = Task.Frequency.values()[cursor.getInt(cursor.getColumnIndex(TaskTable.COLUMN_NAME_FREQUENCY))];
            Task task = new Task(taskId, petIdAux, taskName, scheduleDatetime, description);
            values.add(task);
        }
        cursor.close();

        return values;
    }
}
