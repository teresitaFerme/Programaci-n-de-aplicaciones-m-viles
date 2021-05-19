package es.ucm.fdi.animalcare.feature.calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class TaskLoader extends AsyncTaskLoader<List<Task>> {
    private Integer mUserId;
    private Date mDateTime;
    private Context mContext;
    AnimalCareDbHelper dbHelper;

    public TaskLoader(@NonNull Context context, Integer userId, Date dateTime){
        super(context);
        mContext = context;
        mUserId = userId;
        mDateTime = dateTime;
        dbHelper = new AnimalCareDbHelper(context);
    }

    @Nullable
    @Override
    public List<Task> loadInBackground() {
        //TODO: get tasks (filter by user and datetime)
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        int year = mDateTime.getYear();
        int month = mDateTime.getMonth();
        int day = mDateTime.getDate();
        Date comparison1 = new Date(year, month, day, 0, 0);
        Date comparison2 = new Date(year, month, day, 23, 59);

        String query = "SELECT t." + BaseColumns._ID +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKDONE_DATETIME +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_DESCRIPTION +
                ", t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_FREQUENCY +
                " FROM " + AnimalCareDatabase.TaskTable.TABLE_NAME + " t JOIN " + AnimalCareDatabase.PetTable.TABLE_NAME +
                " p ON t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET + " = p." + BaseColumns._ID + " WHERE p." + AnimalCareDatabase.PetTable.COLUMN_NAME_ID_OWNER +
                "= " + String.valueOf(mUserId) + " ORDER BY t." + AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME;
        Cursor cursor = db.rawQuery(query, null);
        List<Task> values = new ArrayList<>();
        List<Task> finalValues = new ArrayList<>();

        while(cursor.moveToNext()){
            Integer taskId = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable._ID));
            Integer petIdAux = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_ID_PET));
            String taskName = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKNAME));
            String scheduleDatetime = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_SCHEDULE_DATETIME));
            String taskDoneDatetime = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_TASKDONE_DATETIME));
            String description = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_DESCRIPTION));
            Integer freq = cursor.getInt(cursor.getColumnIndex(AnimalCareDatabase.TaskTable.COLUMN_NAME_FREQUENCY));

            Task task = new Task(taskId, petIdAux, taskName, scheduleDatetime, taskDoneDatetime, description, freq, mContext.getResources().getStringArray(R.array.task_frequency_array));
            values.add(task);

            int interval=1, loopLimit=1; // Interval: number of days/weeks/months/years to add to original date

            // Check frequency and insert necessary number of copies of task within a year into database
            switch(freq){
                case Task.FREQUENCY_DAILY:
                    loopLimit = 365;
                    break;
                case Task.FREQUENCY_WEEKLY:
                    loopLimit = 52;
                    break;
                case Task.FREQUENCY_MONTHLY:
                    loopLimit = 12;
                    break;
                case Task.FREQUENCY_YEARLY:
                    loopLimit = 2;
                    break;
                default: // No frequency
                    interval = 0;
            }

            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(dateFormat.parse(scheduleDatetime));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (int i = 1; i < loopLimit; i++){
                switch(freq){
                    case Task.FREQUENCY_DAILY:
                        calendar.add(Calendar.DATE, interval);
                        break;
                    case Task.FREQUENCY_WEEKLY:
                        calendar.add(Calendar.WEEK_OF_YEAR, interval);
                        break;
                    case Task.FREQUENCY_MONTHLY:
                        calendar.add(Calendar.MONTH, interval);
                        break;
                    case Task.FREQUENCY_YEARLY:
                        calendar.add(Calendar.YEAR, interval);
                        break;
                    default: // No frequency: for loop not reachable for this case
                }

                String dateTimeString = dateFormat.format(calendar.getTime());

                task = new Task(taskId, petIdAux, taskName, dateTimeString, taskDoneDatetime, description, freq, mContext.getResources().getStringArray(R.array.task_frequency_array));
                values.add(task);
            }
        }
        cursor.close();

        // Filter tasks by date
        for (Task t : values){
            Date scheduleDatetime = t.getmScheduleDatetime();

            if(scheduleDatetime.compareTo(comparison1) >= 0 && scheduleDatetime.compareTo(comparison2) <= 0) {
                finalValues.add(t);
            }
        }


        return finalValues;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
