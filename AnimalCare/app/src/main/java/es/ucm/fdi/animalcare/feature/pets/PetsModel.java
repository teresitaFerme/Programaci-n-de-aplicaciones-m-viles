package es.ucm.fdi.animalcare.feature.pets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.data.Pets;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.Pet;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class PetsModel extends BaseModel {
    private String mName;
    private String mType;

    AnimalCareDbHelper dbHelper;

    String[] projection = {
            BaseColumns._ID,
            Pet.COLUMN_NAME_NAME,
            Pet.COLUMN_NAME_TYPE,
            Pet.COLUMN_NAME_ID_OWNER
    };

    PetsModel(Context ctx) {
        dbHelper = new AnimalCareDbHelper(ctx);
    }

    public boolean saveNewPet(String name, String type, Integer userId) { //Guarda animal en la base de datos

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Pet.COLUMN_NAME_NAME, name);
        values.put(Pet.COLUMN_NAME_TYPE, type);
        //Guardar tambien el id del usuario
        values.put(Pet.COLUMN_NAME_ID_OWNER, userId);

        long newRowId = db.insert(Pet.TABLE_NAME, null, values);

        if (newRowId == -1) return false;
        else return true;
    }

    public List<Pets> getPets (Integer user_id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = Pet.COLUMN_NAME_ID_OWNER + " = ?";
        String[] selectionArgs = {user_id + ""};

        Cursor cursor = db.query(
                Pet.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List values = new ArrayList<Pets>();
        while(cursor.moveToNext()){
            Pets pet = new Pets();
            pet.setId(cursor.getString(cursor.getColumnIndex(Pet._ID)));
            pet.setName(cursor.getString(cursor.getColumnIndex(Pet.COLUMN_NAME_NAME)));
            pet.setType(cursor.getString(cursor.getColumnIndex(Pet.COLUMN_NAME_TYPE)));
            pet.setIdOwner(cursor.getInt(cursor.getColumnIndex(Pet.COLUMN_NAME_ID_OWNER)));
            values.add(pet);
        }
        cursor.close();

        return values;
    }

}
