package com.pumpit.app.pumpitpro;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.FLAG_ONE_SHOT;

public class InAppActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment segmentSelected = null;
    int activeBtmIconId = 0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app);

        final HomeFragment homeFragment = new HomeFragment();
        final ManageFragment manageFragment = new ManageFragment();
        final StoreFragment storeFragment = new StoreFragment();
        final ProfileFragment profileFragment = new ProfileFragment();

        //getWindow().setStatusBarColor(Color.WHITE);

        //startAlarm(true, true);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if(user==null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        GymName = user.getDisplayName();

        Log.d("GymMetainfo", GymName);

        if(GymName.isEmpty()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        //checkNoti();

        startAlarm();

        // later initialize only once
        DocumentReference planDoc1 = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                "/Plans/Plan1");
        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("planName", "Plan1");
        data1.put("planPrice", "2500");
        data1.put("planDuration", "3 Months Plan");
        data1.put("planFeatures", "Cardio, Strength");
        data1.put("coverId", R.drawable.plan1);
        planDoc1.set(data1);

        DocumentReference planDoc2 = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                "/Plans/Plan2");
        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put("planName", "Plan2");
        data2.put("planPrice", "5000");
        data2.put("planDuration", "6 Months Plan");
        data2.put("planFeatures", "Cardio, Strength");
        data2.put("coverId", R.drawable.plan2);
        planDoc2.set(data2);

        DocumentReference planDoc3 = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                "/Plans/Plan3");
        Map<String, Object> data3 = new HashMap<String, Object>();
        data3.put("planName", "Plan3");
        data3.put("planPrice", "12000");
        data3.put("planDuration", "12 Months Plan");
        data3.put("planFeatures", "Cardio, Strength");
        data3.put("coverId", R.drawable.plan3);
        planDoc3.set(data3);


        //bottomNavigationView.setSelectedItemId(R.id.action_profile); this works

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).addToBackStack(null).commit();
        activeBtmIconId = R.id.action_home;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.action_home:
                        if(activeBtmIconId!=R.id.action_home){
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frag_container, homeFragment, "homefrag")
                                    .addToBackStack(null).commit();
                        }
                        activeBtmIconId = R.id.action_home;
                        break;
                    case R.id.action_manage:
                        if(activeBtmIconId!=R.id.action_manage){
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frag_container, manageFragment, "managefrag")
                                    .addToBackStack(null).commit();
                        }
                        activeBtmIconId = R.id.action_manage;
                        break;
                    case R.id.action_store:
                        if(activeBtmIconId!=R.id.action_store){
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frag_container, storeFragment, "managefrag")
                                    .addToBackStack(null).commit();
                        }
                        activeBtmIconId = R.id.action_store;
                        break;
                    case R.id.action_profile:
                        if(activeBtmIconId!=R.id.action_profile){
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frag_container, profileFragment, "profilefrag")
                                    .addToBackStack(null).commit();
                        }
                        activeBtmIconId = R.id.action_profile;
                        break;
                }
                return true;
            }
        });
    }

//    private void setupInitData() {
//        final ArrayList<Integer> membMetaInfo = new ArrayList<>();
//        membMetaInfo.add(0);
//        membMetaInfo.add(0);
//        CollectionReference fetchMemb = FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members");
//        fetchMemb.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                    //Log.d("planArri", document.getId());
//                    if(Objects.equals(document.get("payment"), "Payment Pending")){
//                        Integer count = membMetaInfo.get(0);
//                        count++;
//                        membMetaInfo.set(0, count);
//                    }
//                    else if(Objects.equals(document.get("payment"), "Fees Paid")){
//                        Integer count1 = membMetaInfo.get(1);
//                        count1++;
//                        membMetaInfo.set(1, count1);
//                    }
//                }
//                DocumentReference updateInitData = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/MetaData/members");
//                updateInitData.update("activemembcount", String.valueOf(membMetaInfo.get(1)));
//                updateInitData.update("overduemembcount", String.valueOf(membMetaInfo.get(0)));
//            }
//        });
//    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==1){
            AlertDialog.Builder builder = new AlertDialog.Builder(InAppActivity.this, R.style.AlertDialogStyle);
            builder.setTitle("Exit");
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                    moveTaskToBack(true);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        else{
            super.onBackPressed();
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.frag_container);
            if(f instanceof HomeFragment){
                bottomNavigationView.setSelectedItemId(R.id.action_home);
            }
            else if(f instanceof ManageFragment){
                bottomNavigationView.setSelectedItemId(R.id.action_manage);
            }
            else if(f instanceof StoreFragment){
                bottomNavigationView.setSelectedItemId(R.id.action_store);
            }
            else if(f instanceof ProfileFragment){
                bottomNavigationView.setSelectedItemId(R.id.action_profile);
            }
        }
    }

//    public void setupPlansWithCount(){
//        final ArrayList<String> planNameList = new ArrayList<>();
//        final ArrayList<Integer> planCountList = new ArrayList<>();
//        CollectionReference cr = FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Plans");
//        cr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                    //Log.d("planArri", document.getId());
//                    if(!planNameList.contains(document.getId())){
//                        planNameList.add(document.getId());
//                    }
//                }
//                CollectionReference membRef = FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members");
//                membRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            Log.d("planNamelst", String.valueOf(planNameList.size()));
//                            for(int i = 0; i<planNameList.size(); i++){
//                                planCountList.add(0);
//                            }
//                            Log.d("plancntsize", String.valueOf(planCountList.size()));
//                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                                for(int i = 0; i<planNameList.size(); i++){
//                                    if(planNameList.get(i).equals(document.getString("planName"))){
//                                        Integer cnt = planCountList.get(i);
//                                        cnt = cnt+1;
//                                        planCountList.set(i, cnt);
//                                    }
//                                }
//                            }
//                            for(int j = 0; j<planCountList.size();j++){
//                                Log.d("plancntval", String.valueOf(planCountList.get(j)));
//                            }
//                        }
//                    }
//                });
//            }
//        });
//    }

    public void checkNoti(){
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);

        Intent myIntent = new Intent(getApplicationContext(), InAppActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                myIntent,
                FLAG_ONE_SHOT );

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Manage Gym")
                .setSmallIcon(R.drawable.iconapp)
                .setColor(getResources().getColor(R.color.gtstrtbck))
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.iconsplash))
                .setContentIntent(pendingIntent)
                .setContentText("Test Text")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Action");

        notificationManager.notify(notificationId, builder.build());
    }

    private void startAlarm(){
        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;
        GymName = user.getDisplayName();
        // SET TIME HERE
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 13);


        myIntent = new Intent(this, AlarmNotificationReceiver.class);
        myIntent.putExtra("gymdispname", GymName);
        pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
