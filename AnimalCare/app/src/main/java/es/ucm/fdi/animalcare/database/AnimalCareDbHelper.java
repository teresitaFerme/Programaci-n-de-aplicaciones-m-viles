package es.ucm.fdi.animalcare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.User;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.Pet;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase.Task;

public class AnimalCareDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AnimalCare.db";
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE " + User.TABLE_NAME + " (" +
                    User._ID + " INTEGER PRIMARY KEY," +
                    User.COLUMN_NAME_NAME + " TEXT NOT NULL," +
                    User.COLUMN_NAME_USERNAME + " TEXT UNIQUE NOT NULL," +
                    User.COLUMN_NAME_PASSWORD + " TEXT NOT NULL);";
    private static final String SQL_CREATE_PET_TABLE =
            "CREATE TABLE " + Pet.TABLE_NAME + " (" +
                    Pet._ID + " INTEGER PRIMARY KEY," +
                    Pet.COLUMN_NAME_NAME + " TEXT NOT NULL," +
                    Pet.COLUMN_NAME_TYPE + " TEXT NOT NULL," +
                    Pet.COLUMN_NAME_ID_OWNER + " INTEGER, FOREIGN KEY (" + Pet.COLUMN_NAME_ID_OWNER + ") REFERENCES " +
                    User.TABLE_NAME + "(" + User._ID + "));";
    private static final String SQL_CREATE_TASK_TABLE =
            "CREATE TABLE " + Task.TABLE_NAME + " (" +
                    Task._ID + " INTEGER PRIMARY KEY," +
                    Task.COLUMN_NAME_NAME + " TEXT NOT NULL," +
                    Task.COLUMN_NAME_SCHEDULE_DATETIME + " DATETIME NOT NULL," +
                    Task.COLUMN_NAME_TASKDONE_DATETIME + " DATETIME NOT NULL," +
                    Task.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    Task.COLUMN_NAME_FREQUENCY + "TEXT," +
                    Task.COLUMN_NAME_ID_PET + " INTEGER, FOREIGN KEY (" + Task.COLUMN_NAME_ID_PET + ") REFERENCES " +
                    Pet.TABLE_NAME + "(" + Pet._ID + "));";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + User.TABLE_NAME;

    public AnimalCareDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_PET_TABLE);
        db.execSQL(SQL_CREATE_TASK_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}