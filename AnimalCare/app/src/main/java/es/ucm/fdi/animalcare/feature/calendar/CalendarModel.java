package es.ucm.fdi.animalcare.feature.calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.provider.BaseColumns;
import android.util.Log;

import com.applandeo.materialcalendarview.EventDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseModel;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class CalendarModel extends BaseModel {
    Context ctx;
    AnimalCareDbHelper dbHelper;

    CalendarModel(Context ctx) {
        this.ctx = ctx;
        dbHelper = new AnimalCareDbHelper(ctx);
    }

    public List<Task> getAllTasks (Integer userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT t." + BaseColumns._ID +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKDONE_DATETIME +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_DESCRIPTION +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_FREQUENCY +
                " FROM " + AnimalCareDatabase.TaskTable.TABLE_NAME + " t JOIN " + AnimalCareDatabase.PetTable.TABLE_NAME +
                " p ON t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET + " = p." + BaseColumns._ID + " WHERE p." + AnimalCareDatabase.PetTable.COLUMN_NAME_ID_OWNER +
                "= " + String.valueOf(userId) + " ORDER BY t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME;
        Cursor cursor = db.rawQuery(query, null);
        List values = new ArrayList<Task>();

        while(cursor.moveToNext()){

            //Date taskDoneDatetime;
            Integer taskId = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable._ID));
            Integer petIdAux = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET));
            String taskName = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME));
            String scheduleDatetime = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME));
            String taskDoneDatetime = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKDONE_DATETIME));
            String description = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_DESCRIPTION));
            Integer freq = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_FREQUENCY));
            Task task = new Task(taskId, petIdAux, taskName, scheduleDatetime, taskDoneDatetime, description, freq, ctx.getResources().getStringArray(R.array.task_frequency_array));
            values.add(task);
        }
        cursor.close();

        return values;
    }

    public List<EventDay> getEvents(Integer userId){
        List<Task> tasks = getAllTasks(userId);
        List<EventDay> events = new ArrayList<>();

        for (Task t : tasks){
            Calendar calendar = Calendar.getInstance();
            Date scheduleDateTime = t.getmScheduleDatetime();
            int year = scheduleDateTime.getYear() + 1900;
            int month = scheduleDateTime.getMonth();
            int day = scheduleDateTime.getDate();
            int hours = scheduleDateTime.getHours();
            int mins = scheduleDateTime.getMinutes();

            String datetime = Integer.toString(year) + " " + Integer.toString(month) + " " + Integer.toString(day)
                    + " " + Integer.toString(hours) + " " + Integer.toString(mins);

            calendar.set(year, month, day, hours, mins);

            // Get pet type then set icon
            int icon = R.drawable.bird_green;
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String query = "SELECT p." + BaseColumns._ID +
                    ", p." + AnimalCareDatabase.PetTable.COLUMN_NAME_TYPE +
                    " FROM " + AnimalCareDatabase.PetTable.TABLE_NAME + " p WHERE p." + BaseColumns._ID +
                    "= " + String.valueOf(t.getmPetId());

            Cursor cursor = db.rawQuery(query, null);

            while(cursor.moveToNext()){
                String type = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.PetTable.COLUMN_NAME_TYPE));

                switch(type){
                    case "Perro":
                        icon = R.drawable.dog_green;
                        break;
                    case "Gato":
                        icon = R.drawable.cat_green;
                        break;
                    case "Pajaro":
                        icon = R.drawable.bird_green;
                        break;
                    case "Pez":
                        icon = R.drawable.fish_green;
                        break;
                    case "Tortuga":
                        icon = R.drawable.turtle_green;
                        break;
                    default: // "Caballo"
                        icon = R.drawable.horse_green;
                }
            }

            events.add(new EventDay(calendar, icon)); //TODO: set logic for setting icons
        }

        return events;
    }
}
