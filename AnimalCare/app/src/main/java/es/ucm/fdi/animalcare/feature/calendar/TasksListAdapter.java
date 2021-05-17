package es.ucm.fdi.animalcare.feature.calendar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.database.AnimalCareDatabase;
import es.ucm.fdi.animalcare.database.AnimalCareDbHelper;

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.ViewHolder>{
    private List<Task> mTasksData;
    private LayoutInflater mInflater;
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TasksListAdapter mAdapter;
        TextView taskSchedule, petName, taskName;

        public ViewHolder(@NonNull View itemView, TasksListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            taskSchedule = itemView.findViewById(R.id.taskScheduleTime);
            petName = itemView.findViewById(R.id.taskListPetName);
            taskName = itemView.findViewById(R.id.taskName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {}
    }

    public TasksListAdapter(Context context, List<Task> data){
        mInflater = LayoutInflater.from(context);
        mTasksData = data;
        mContext = context;
    }

    public void setTasksData(List<Task> data){
        mTasksData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task mCurrent = mTasksData.get(position);

        // Convert schedule to String
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date sched = mCurrent.getmScheduleDatetime();
        String scheduleString = dateFormat.format(sched);

        String mPetName = getPetName(mCurrent.getmPetId());

        holder.taskSchedule.setText(scheduleString);
        holder.petName.setText(mPetName);
        holder.taskName.setText(mCurrent.getmTaskName());
    }

    @Override
    public int getItemCount() {
        return mTasksData.size();
    }

    private String getPetName(int petId){
        String ret = "";

        // Get database
        AnimalCareDbHelper dbHelper = new AnimalCareDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query
        String query = "SELECT " + AnimalCareDatabase.PetTable.COLUMN_NAME_PETNAME +
                " FROM " + AnimalCareDatabase.PetTable.TABLE_NAME +
                " WHERE " + BaseColumns._ID + " = " + String.valueOf(petId);

        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            ret = cursor.getString(cursor.getColumnIndex(AnimalCareDatabase.PetTable.COLUMN_NAME_PETNAME));
        }
        cursor.close();

        return ret + ":";
    }
}
