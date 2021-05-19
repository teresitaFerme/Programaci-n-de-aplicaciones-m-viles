package es.ucm.fdi.animalcare.feature.register;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.UserTable;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class RegisterModel extends BaseModel {

    AnimalCareDbHelper dbHelper;

    RegisterModel(Context ctx) {
        dbHelper = new AnimalCareDbHelper(ctx);
    }

    public boolean getUsername(String username) {

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserTable.COLUMN_NAME_USERNAME
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserTable.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                UserTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToFirst()) return true;
        else return false;
    }

    public boolean registerUser(String name, String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMN_NAME_NAME, name);
        values.put(UserTable.COLUMN_NAME_USERNAME, username);
        values.put(UserTable.COLUMN_NAME_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserTable.TABLE_NAME, null, values);

        if (newRowId == -1) return false;
        else{
            App.getApp().setUser(User.getInstance(name, username, (int) newRowId));
            return true;
        }
    }
}
