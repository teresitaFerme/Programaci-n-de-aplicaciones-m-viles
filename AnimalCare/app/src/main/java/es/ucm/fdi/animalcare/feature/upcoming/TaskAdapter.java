package es.ucm.fdi.animalcare.feature.upcoming;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.Task;

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Task> mData;
    private LayoutInflater mInflater;
    private Context ctext;
    private int numTodayTask;
    private int numTomorrowTask;
    private int numAfterTomorrowTask;

    public TaskAdapter(List<Task> itemList, Context context){
        Calendar calendar = Calendar.getInstance();
        this.mInflater = LayoutInflater.from(context);
        this.ctext = context;
        this.mData = itemList;
        numTodayTask = 0;
        numTomorrowTask = 0;
        numAfterTomorrowTask = 0;

        Date dateToday = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateTomorrow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateAfterTomorrow = calendar.getTime();
        List<Task> toDelete = new ArrayList<>();

        for(Task t : itemList){
            if(t.getmScheduleDatetime().after(dateToday) && t.getmScheduleDatetime().before(dateTomorrow))
                numTodayTask++;
            else if(t.getmScheduleDatetime().after(dateTomorrow) && t.getmScheduleDatetime().before(dateAfterTomorrow))
                numTomorrowTask++;
            else if(t.getmScheduleDatetime().after(dateAfterTomorrow))
                numAfterTomorrowTask++;
            else
                toDelete.add(t);
        }
        itemList.removeAll(toDelete);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.task_list_item, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = mInflater.inflate(R.layout.task_header, parent, false);
            return new HeaderViewHolder(view);
        }
        else return null;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //holder.bindData(mData.get(position));

        if (holder instanceof HeaderViewHolder){
            //setheadersdata_flag = true;
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            String labelStr = "";
            if (position == 0)
                labelStr = "Today";
            else if (position == numTodayTask + 1)
                labelStr = "Tomorrow";
            else if (position == numTomorrowTask + numTodayTask + 2)
                labelStr = "Soon";

            headerViewHolder.label.setText(labelStr);
        }
        else if (holder instanceof ItemViewHolder){
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            int realPosition = 0;
            if (position > 0 && position <= numTodayTask)
                realPosition = position - 1;
            else if (position > numTodayTask + 1 && position <= numTodayTask + numTomorrowTask + 1)
                realPosition = position - 2;
            else if (position > numTomorrowTask + numTodayTask + 2)
                realPosition =  position - 3;

            itemViewHolder.bindData(mData.get(realPosition));
        }
    }

    public void setItems(List<Task> data){
        mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == numTodayTask + 1
                || position == numTodayTask + numTomorrowTask + 2)  {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mData.size() + 3;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView label;

        public HeaderViewHolder(View view) {
            super(view);
            label = view.findViewById(R.id.taskTodayLabel);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView name, time, desc;
        Button editButton, removeButton;
        ConstraintLayout cLayout;

        ItemViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.taskName);
            time = itemView.findViewById(R.id.taskScheduleTime);
            desc = itemView.findViewById(R.id.taskDesc);
            editButton = itemView.findViewById(R.id.editTaskButton);
            removeButton = itemView.findViewById(R.id.removeTaskButton);
            cLayout = itemView.findViewById(R.id.taskView);
            int h = cLayout.getHeight();
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        void bindData(Task task){
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String timeStr = dateFormat.format(task.getmScheduleDatetime());
            time.setText(timeStr);
            name.setText(task.getmTaskName());
            desc.setText(task.getmDescription());
            editButton.setOnClickListener(v -> {
                ((UpcomingActivity)ctext).editTask(task); });
            removeButton.setOnClickListener(v -> { ((UpcomingActivity)ctext).removeTask(task.getmId()); });

            desc.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            removeButton.setVisibility(View.INVISIBLE);
            cLayout.setMaxHeight(150);

            name.setOnClickListener(v -> {
                if(desc.getVisibility() == View.INVISIBLE){
                    desc.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.VISIBLE);
                    removeButton.setVisibility(View.VISIBLE);
                    cLayout.setMaxHeight(700);
                } else {
                    desc.setVisibility(View.INVISIBLE);
                    editButton.setVisibility(View.INVISIBLE);
                    removeButton.setVisibility(View.INVISIBLE);
                    cLayout.setMaxHeight(150);
                }
            });
        }
    }
}
