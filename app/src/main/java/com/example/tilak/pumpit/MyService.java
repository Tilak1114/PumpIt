package com.example.tilak.pumpit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

public class MyService extends Service {
    public MyService(){

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        startAlarm();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
    private void startAlarm(){
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        // SET TIME HERE
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);


        myIntent = new Intent(this, AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
