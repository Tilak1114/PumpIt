package com.example.tilak.pumpit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import static android.app.PendingIntent.FLAG_ONE_SHOT;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);

        Intent myIntent = new Intent(context, InAppActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                FLAG_ONE_SHOT );

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Manage Gym")
                .setSmallIcon(R.drawable.notificationicon)
                .setColor(context.getResources().getColor(R.color.gtstrtbck))
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.iconsplash))
                .setContentIntent(pendingIntent)
                .setContentText("Test Text")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Action");

        notificationManager.notify(notificationId, builder.build());
    }
}
