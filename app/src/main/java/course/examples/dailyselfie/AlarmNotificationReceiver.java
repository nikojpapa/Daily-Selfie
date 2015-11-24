package course.examples.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    private static final String TAG= "ds-alarmnotificationreceiver";
//    public AlarmNotificationReceiver() {
//    }
    private static final int MY_NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Intent startMain= new Intent(context, MainActivity.class);

        PendingIntent startMainPending= PendingIntent.getActivity(context,
                0,
                startMain,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder notificationBuilder= new Notification.Builder(context)
                .setTicker("Time to take a selfie!")
                .setSmallIcon(R.drawable.ic_camera_alt_black_24dp)
                .setContentTitle("Daily Selfie")
                .setContentText("It's that time of day!")
                .setContentInfo("I wonder what this shows")
                .setContentIntent(startMainPending)
                .setAutoCancel(true);

        NotificationManager notificationManager= (NotificationManager) context.getSystemService
                (context.NOTIFICATION_SERVICE);

        notificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
        Toast.makeText(context, "Notification should be made", Toast.LENGTH_SHORT).show();
    }
}
