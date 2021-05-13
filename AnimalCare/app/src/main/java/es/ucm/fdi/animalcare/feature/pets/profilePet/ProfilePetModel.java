package es.ucm.fdi.animalcare.feature.pets.profilePet;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import es.ucm.fdi.animalcare.base.BaseModel;
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
}
