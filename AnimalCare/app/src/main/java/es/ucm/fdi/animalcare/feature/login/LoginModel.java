package es.ucm.fdi.animalcare.feature.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.UserTable;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class LoginModel extends BaseModel {
    private String mName;
    private String mUsername;
    private String mPassword;
    private Integer mId;

    AnimalCareDbHelper dbHelper;

    // Define a projection that specifies which columns from the database
    // you will actually use after this query.
    String[] projection = {
            BaseColumns._ID,
            UserTable.COLUMN_NAME_NAME,
            UserTable.COLUMN_NAME_USERNAME,
            UserTable.COLUMN_NAME_PASSWORD
    };

    LoginModel(Context ctx){
        mName = "name";
        mUsername = "username";
        mPassword = "password";
        dbHelper = new AnimalCareDbHelper(ctx);
    }

    public boolean validateLogin(String username, String password) {
        //esto realmente es comprobar que existe ese user en la bbdd

        // Gets the data repository in read mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

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

        if(cursor.moveToFirst()) {
            mName = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NAME_NAME));
            mUsername = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NAME_USERNAME));
            mPassword = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NAME_PASSWORD));
            mId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
            cursor.close();
        } else return false;

        if(mUsername.equals(username) && mPassword.equals(password)){
            App.getApp().setUser(User.getInstance(mName, mUsername, mId));
            return true;
        }
        else return false;
    }
}
