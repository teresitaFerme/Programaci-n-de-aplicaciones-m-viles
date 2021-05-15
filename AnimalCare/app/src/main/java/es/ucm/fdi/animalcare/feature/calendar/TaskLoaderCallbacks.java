package es.ucm.fdi.animalcare.feature.calendar;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import es.ucm.fdi.animalcare.data.Task;

public class TaskLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Task>>{
    static final String EXTRA_DATETIMESTRING = "dateTimeString";
    static final String EXTRA_USERID = "userId";

    private Context mContext;
    private CalendarActivity mActivity;

    public TaskLoaderCallbacks(Context context, CalendarActivity activity){
        mContext = context;
        mActivity = activity;
    }

    @NonNull
    @Override
    public Loader<List<Task>> onCreateLoader(int id, @Nullable Bundle args) {
        String dateTimeString = args.getString(EXTRA_DATETIMESTRING);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date dateTime = null;

        try{
            dateTime = dateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return new TaskLoader(mContext, args.getInt(EXTRA_USERID), dateTime);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Task>> loader, List<Task> data) {
        mActivity.updateTasksList(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Task>> loader) { }
}
