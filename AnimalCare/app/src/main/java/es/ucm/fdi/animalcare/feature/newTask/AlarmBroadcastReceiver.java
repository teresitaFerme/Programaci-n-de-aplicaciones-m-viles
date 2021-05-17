package es.ucm.fdi.animalcare.feature.newTask;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.feature.showTask.ShowTaskActivity;
import es.ucm.fdi.animalcare.feature.showTask.ShowTaskModel;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static String CHANNEL_ID = "TASK_NOTIFICATION";
    @Override
    public void onReceive(Context context, Intent intent) {
        ShowTaskModel mShowTaskModel = new ShowTaskModel(context);
        Task task = mShowTaskModel.getTaskById(intent.getIntExtra("taskId", -1));

        //Send notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(CHANNEL_ID, "Pending Task",
                            NotificationManager.IMPORTANCE_DEFAULT);
        }

        if(task != null){
            Intent notifIntent = new Intent(context, ShowTaskActivity.class);
            notifIntent.putExtra("taskId", task.getmId());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, task.getmId(), notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder mBuilder = new
                    NotificationCompat.Builder(context, CHANNEL_ID)
                    //setSmallIcon()
                    .setContentTitle("Pending Task:")
                    .setContentText(task.getmTaskName())
                    .setContentIntent(pendingIntent)

                    .setAutoCancel(true);


            NotificationManager mNotifyManager = (NotificationManager)
                    context.getSystemService(context.NOTIFICATION_SERVICE);

            Notification myNotification = mBuilder.build();
            mNotifyManager.notify(task.getmId(), myNotification);
        }

        //Reassign task
        //Delete alarm?
    }
}