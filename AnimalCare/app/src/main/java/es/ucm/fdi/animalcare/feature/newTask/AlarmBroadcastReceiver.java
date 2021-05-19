package es.ucm.fdi.animalcare.feature.newTask;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.feature.showTask.ShowTaskActivity;

import static android.content.Context.ALARM_SERVICE;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static String CHANNEL_ID = "TASK_NOTIFICATION";
    private NotificationManager mNotifyManager;
    private Context context;
    private Task task;
    private NewTaskModel mNewTaskModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        mNewTaskModel = new NewTaskModel(context);
        task = mNewTaskModel.getTaskById(intent.getIntExtra("taskId", -1));
        mNotifyManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        if(task.getmTaskDoneDatetime() == null){
            createNotificationChannel();
            createNotificationAndNotify();

        }
        reassignTask();
    }

    private void reassignTask() {
        Date date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(task.getmScheduleDatetime());

        switch(task.getmFreq()){
            case Task.FREQUENCY_DAILY:
                calendar.add(Calendar.DATE, 1);
                break;
            case Task.FREQUENCY_WEEKLY:
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case Task.FREQUENCY_MONTHLY:
                calendar.add(Calendar.MONDAY, 1);
                break;
            case Task.FREQUENCY_YEARLY:
                calendar.add(Calendar.YEAR, 1);
                break;
            default:
                cancelAlarm(context, task.getmId());
                return;
        }
        date = calendar.getTime();
        task.setmScheduleDatetime(date);
        String dateStr = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(date);

        if(mNewTaskModel.saveNewTask(task.getmTaskName(), task.getmDescription(),
                dateStr, task.getmPetId(), task.getmFreq()) > 0){
            setAlarm(context, task.getmId(), task.getmScheduleDatetime());
        }
    }


    private void createNotificationAndNotify() {
        if (task != null) {
            Intent notifIntent = new Intent(context, ShowTaskActivity.class);
            notifIntent.putExtra("taskId", task.getmId());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, task.getmId(), notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder mBuilder = new
                    NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_upcoming)
                    .setContentTitle("Pending Task:")
                    .setContentText(task.getmTaskName())
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setAutoCancel(true);
            mNotifyManager.notify(task.getmId(), mBuilder.build());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Pending Task",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public void setAlarm(Context context, int taskId, Date date){
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("taskId", taskId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, taskId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
    }


    public void cancelAlarm(Context context, Integer taskId) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("taskId", taskId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, taskId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}