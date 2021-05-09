package es.ucm.fdi.animalcare.feature.password;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class PasswordModel extends BaseModel {
    AnimalCareDbHelper dbHelper;

    public PasswordModel(Context ctx){dbHelper = new AnimalCareDbHelper(ctx);}

    protected boolean validatePW(String username, String password){
        String mPassword;

        // Gets the data repository in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Filter results
        String selection = AnimalCareDatabase.UserTable.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {username};

        String[] projection = {AnimalCareDatabase.UserTable.COLUMN_NAME_PASSWORD};

        Cursor cursor = db.query(
                AnimalCareDatabase.UserTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToFirst()) {
            mPassword = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.UserTable.COLUMN_NAME_PASSWORD));
            cursor.close();
        } else return false;

        if(mPassword.equals(password)) return true;
        else return false;
    }

    public void changePassword(String username, String password){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        String whereClause = AnimalCareDatabase.UserTable.COLUMN_NAME_USERNAME + " = ?";

        contentValues.put(AnimalCareDatabase.UserTable.COLUMN_NAME_PASSWORD, password);

        db.update(AnimalCareDatabase.UserTable.TABLE_NAME, contentValues, whereClause, new String[]{username});
    }
}
