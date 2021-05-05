package es.ucm.fdi.animalcare.feature.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class LoginModel extends BaseModel {
    private String mUsername;
    private String mPassword;
    private Integer mId;

    AnimalCareDbHelper dbHelper;

    // Define a projection that specifies which columns from the database
    // you will actually use after this query.
    String[] projection = {
            BaseColumns._ID,
            AnimalCareDatabase.User.COLUMN_NAME_USERNAME,
            AnimalCareDatabase.User.COLUMN_NAME_PASSWORD
    };

    LoginModel(Context ctx){
        mUsername = "username";
        mPassword = "password";
        dbHelper = new AnimalCareDbHelper(ctx);
    }

    public es.ucm.fdi.animalcare.data.User validateLogin(String username, String password) {
        //esto realmente es comprobar que existe ese user en la bbdd

        // Gets the data repository in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = AnimalCareDatabase.User.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                AnimalCareDatabase.User.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToFirst()) {
            mUsername = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.User.COLUMN_NAME_USERNAME));
            mPassword = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.User.COLUMN_NAME_PASSWORD));
            mId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
            cursor.close();
        } else return null;

        if(mUsername.equals(username) && mPassword.equals(password)) return new User(mUsername, mId);
        else return null;
    }
}
