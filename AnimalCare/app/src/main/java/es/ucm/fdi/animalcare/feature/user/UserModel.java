package es.ucm.fdi.animalcare.feature.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class UserModel extends BaseModel {

    AnimalCareDbHelper dbHelper;

    UserModel(Context ctx) {
        dbHelper = new AnimalCareDbHelper(ctx);
    }

    public void setName(String username, String name){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        String whereClause = AnimalCareDatabase.UserTable.COLUMN_NAME_USERNAME + " = ?";

        contentValues.put(AnimalCareDatabase.UserTable.COLUMN_NAME_NAME, name);

        db.update(AnimalCareDatabase.UserTable.TABLE_NAME, contentValues, whereClause, new String[]{username});
    }
}
