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
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.PetTable;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class PetsModel extends BaseModel {

    AnimalCareDbHelper dbHelper;

    String[] projection = {
            BaseColumns._ID,
            PetTable.COLUMN_NAME_PETNAME,
            PetTable.COLUMN_NAME_TYPE,
            PetTable.COLUMN_NAME_ID_OWNER
    };

    PetsModel(Context ctx) {
        dbHelper = new AnimalCareDbHelper(ctx);
    }


    public List<Pets> getPets (Integer userId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = PetTable.COLUMN_NAME_ID_OWNER + " = ?";
        String[] selectionArgs = {userId + ""};

        Cursor cursor = db.query(
                PetTable.TABLE_NAME,   // The table to query
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
            pet.setId(cursor.getInt(cursor.getColumnIndex(PetTable._ID)));
            pet.setName(cursor.getString(cursor.getColumnIndex(PetTable.COLUMN_NAME_PETNAME)));
            pet.setType(cursor.getString(cursor.getColumnIndex(PetTable.COLUMN_NAME_TYPE)));
            pet.setIdOwner(cursor.getInt(cursor.getColumnIndex(PetTable.COLUMN_NAME_ID_OWNER)));
            values.add(pet);
        }
        cursor.close();

        return values;
    }

}