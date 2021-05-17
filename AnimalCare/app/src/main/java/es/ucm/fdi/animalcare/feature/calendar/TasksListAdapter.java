package es.ucm.fdi.animalcare.feature.calendar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.ViewHolder>{
    private List<Task> mTasksData;
    private LayoutInflater mInflater;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TasksListAdapter mAdapter;
        TextView taskSchedule, taskName;

        public ViewHolder(@NonNull View itemView, TasksListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            taskSchedule = itemView.findViewById(R.id.taskScheduleTime);
            taskName = itemView.findViewById(R.id.taskName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {}
    }

    public TasksListAdapter(Context context, List<Task> data){
        mInflater = LayoutInflater.from(context);
        mTasksData = data;
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

        holder.taskSchedule.setText(scheduleString);
        holder.taskName.setText(mCurrent.getmTaskName());
    }

    @Override
    public int getItemCount() {
        return mTasksData.size();
    }
}
