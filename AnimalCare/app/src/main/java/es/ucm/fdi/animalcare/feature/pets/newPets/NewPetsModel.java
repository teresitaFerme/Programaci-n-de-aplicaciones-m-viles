package es.ucm.fdi.animalcare.feature.pets.newPets;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class NewPetsModel extends BaseModel {

    AnimalCareDbHelper dbHelper;

    NewPetsModel(Context ctx) {
        dbHelper = new AnimalCareDbHelper(ctx);
    }

    public boolean saveNewPet(String name, String type, Integer userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AnimalCareDatabase.PetTable.COLUMN_NAME_PETNAME, name);
        values.put(AnimalCareDatabase.PetTable.COLUMN_NAME_TYPE, type);
        values.put(AnimalCareDatabase.PetTable.COLUMN_NAME_ID_OWNER, userId);

        long newRowId = db.insert(AnimalCareDatabase.PetTable.TABLE_NAME, null, values);

        if (newRowId == -1) return false;
        else return true;
    }
}
