package es.ucm.fdi.animalcare.feature.upcoming;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> mData;
    private LayoutInflater mInflater;
    private Context ctext;

    public TaskAdapter(List<Task> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.ctext = context;
        this.mData = itemList;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.task_list_item, parent, false);
        return new TaskAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Task> data){
        mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView desc;

        ViewHolder(View itemView){
            super(itemView);
            desc = itemView.findViewById(R.id.taskName);
        }

        void bindData(final Task task){
            desc.setText(task.getmTaskName());
        }
    }
}
