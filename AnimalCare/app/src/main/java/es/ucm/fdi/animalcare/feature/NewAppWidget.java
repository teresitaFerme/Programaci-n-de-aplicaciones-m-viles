package es.ucm.fdi.animalcare.feature;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingPresenter;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        Task task = retrieveUpcomingTask(context);
        if(task != null){
            views.setTextViewText(R.id.tarea_name, String.valueOf(task.getmTaskName()));
            views.setTextViewText(R.id.mascota_name, getPetName(task.getmPetId(), context));
            views.setTextViewText(R.id.date_name, String.valueOf(task.getmScheduleDatetime()));
        }else{
            views.setTextViewText(R.id.tarea_name, "");
            views.setTextViewText(R.id.mascota_name, "");
            views.setTextViewText(R.id.date_name, "");
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static CharSequence getPetName(Integer getmPetId, Context context) {
        AnimalCareDbHelper dbHelper = new AnimalCareDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                AnimalCareDatabase.PetTable.COLUMN_NAME_PETNAME
        };

        String selection = AnimalCareDatabase.PetTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(getmPetId)};

        Cursor cursor = db.query(
                AnimalCareDatabase.PetTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToFirst()) {
            return cursor.getColumnName(1);
        } else return null;
    }

    private static Task retrieveUpcomingTask(Context context) {
        AnimalCareDbHelper dbHelper = new AnimalCareDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT t." + BaseColumns._ID +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME +
                " FROM " + AnimalCareDatabase.TaskTable.TABLE_NAME + " t JOIN " + AnimalCareDatabase.PetTable.TABLE_NAME +
                " p ON t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET + " = p." + BaseColumns._ID + " WHERE p." + AnimalCareDatabase.PetTable.COLUMN_NAME_ID_OWNER +
                "= " + String.valueOf(App.getApp().getUserId()) + " ORDER BY t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME;
        Cursor cursor = db.rawQuery(query, null);

        Task task = null;

        if(cursor.moveToFirst()){
            Integer taskId = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable._ID));
            Integer petIdAux = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET));
            String taskName = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME));
            String scheduleDatetime = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME));
            task = new Task(taskId, petIdAux, taskName, scheduleDatetime, null, null, null, context.getResources().getStringArray(R.array.task_frequency_array));
        }

        return task;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}