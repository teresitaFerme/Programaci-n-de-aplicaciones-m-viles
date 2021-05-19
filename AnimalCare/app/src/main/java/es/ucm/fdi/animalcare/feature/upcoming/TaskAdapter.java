package es.ucm.fdi.animalcare.feature.upcoming;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.App;
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

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateToday = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dateTomorrow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
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
            Resources res = App.getApp().getResources();
            if (position == 0)
                labelStr = res.getString(R.string.tasks_today_header);
            else if (position == numTodayTask + 1)
                labelStr = res.getString(R.string.tasks_tomorrow_header);
            else if (position == numTomorrowTask + numTodayTask + 2)
                labelStr = res.getString(R.string.tasks_soon_header);

            headerViewHolder.label.setText(labelStr);
        }
        else if (holder instanceof ItemViewHolder){
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            int realPosition = 0;
            if (position > 0 && position <= numTodayTask)
                realPosition = position - 1;
            else if (position > numTodayTask + 1 && position <= numTodayTask + numTomorrowTask + 1)
                realPosition = position - 2;
            else if (position > numTomorrowTask + numTodayTask + 2) {
                realPosition = position - 3;
            }

            itemViewHolder.bindData(mData.get(realPosition));
            if (position > numTomorrowTask + numTodayTask + 2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                String timeStr = dateFormat.format(((ItemViewHolder) holder).getTask().getmScheduleDatetime());
                ((ItemViewHolder) holder).time.setText(timeStr);
            }
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
        TextView name, time, petName, status;
        Task task;

        ItemViewHolder(View itemView){
            super(itemView);
            petName = itemView.findViewById(R.id.taskListPetName);
            name = itemView.findViewById(R.id.taskName);
            time = itemView.findViewById(R.id.taskScheduleTime);
            status = itemView.findViewById(R.id.taskStatus);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        void bindData(Task task){
            this.task = task;
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String timeStr = dateFormat.format(task.getmScheduleDatetime());
            time.setText(timeStr);
            name.setText(task.getmTaskName());
            petName.setText(((UpcomingActivity) ctext).getPetName(task.getmPetId()) + ":");
            if(task.getmTaskDoneDatetime() != null)
                status.setText(App.getApp().getResources().getString(R.string.done_task_button));
            else status.setText(App.getApp().getResources().getString(R.string.undone_task_button));


            time.setOnClickListener(v-> { ((UpcomingActivity)ctext).showTask(task.getmId());});
            name.setOnClickListener(v-> { ((UpcomingActivity)ctext).showTask(task.getmId());});
            petName.setOnClickListener(v-> { ((UpcomingActivity)ctext).showTask(task.getmId());});
            status.setOnClickListener(v-> { ((UpcomingActivity)ctext).showTask(task.getmId());});
        }

        public Task getTask(){
            return task;
        }
    }
}
