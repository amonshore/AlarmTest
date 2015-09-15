package it.amonshore.alarmtest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Calgia on 15/09/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public final static String EXTRA_TAG = "AR_ET";
    public final static int NOTIFICATION_ID = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
        String tag = intent.getStringExtra(EXTRA_TAG);
        if (tag == null) {
            tag = "bho";
        }
        Log.i("ALARM", "alarm received: " + tag);
        //Toast.makeText(context, "alarm received: " + tag, Toast.LENGTH_SHORT).show();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Alarm received")
                        .setContentText(tag)
                        .setContentIntent(PendingIntent.getActivity(context, 0,
                                new Intent(context, MainActivity.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                        .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //una eventuale altra notifica con lo stesso ID viene sostituita
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
