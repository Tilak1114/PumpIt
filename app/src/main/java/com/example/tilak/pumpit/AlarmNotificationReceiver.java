package com.example.tilak.pumpit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.Objects;

import static android.app.PendingIntent.FLAG_ONE_SHOT;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    NotificationCompat.Builder builder;
    @Override
    public void onReceive(Context context, Intent intent) {
        final NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        String GymName = intent.getStringExtra("gymdispname");

        final int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        builder = new NotificationCompat.Builder(context, channelId);

        Intent myIntent = new Intent(context, OverdueMembActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                FLAG_ONE_SHOT );

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Overdue Members")
                .setSmallIcon(R.drawable.notificationicon)
                .setColor(context.getResources().getColor(R.color.gtstrtbck))
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.iconsplash))
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Action");

        FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members/")
                .whereLessThanOrEqualTo("endTimeStamp", new Timestamp(new Date()))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(Objects.requireNonNull(task.getResult()).size()>0){
                        builder.setContentText(task.getResult().size()+" Membership(s) Expired today");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            document.getReference().update("payment", "Payment Pending");
                        }
                        notificationManager.notify(notificationId, builder.build());
                    }
                }
            }
        });

    }
}
