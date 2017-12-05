package cmps121.focus;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStreamWriter;

import static android.content.ContentValues.TAG;

/**
 * Created by justin on 11/15/2017.
 */

public class MyService extends Service {
    public void notificationCaller() {
        Intent intent = new Intent(this, cmps121.focus.Start.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        String channel00= "channel00";
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channel00, "channelName", NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        nManager.createNotificationChannel(channel);

        android.support.v4.app.NotificationCompat.Builder NB =new android.support.v4.app.NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Your egg is dying!")
                .setContentText("Click here to save it!")
                .setChannelId(channel00)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setContentIntent(pi);

        nManager.notify(1, NB.build());
//        mNH = new NotficationHelper(this);
//        android.support.v4.app.NotificationCompat.Builder nb = mNH.getChannelNotification("Your egg is dying!", "Click here to save it");
//        mNH.getNotificationManager().notify(1, nb.build());
    }


    public CountDownTimer countDownTimer;


    public long ms;
    public long temp;
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences read = getSharedPreferences("taskAtHand", MODE_PRIVATE);
        String text = read.getString("taskAtHand", "No name defined");
        String time = read.getString("time", "No name defined");
        float timeInt = Float.valueOf(time.split(" ")[0]);
        String hour = time.split(" ")[1];

        if (hour.equals("minutes")) {
            ms = (long) timeInt * 60000;
            temp = (long) timeInt * 60000;
        } else {
            ms = (long) timeInt * 3600000;
            temp = (long) timeInt * 3600000;
        }
        countDownTimer = new CountDownTimer(ms, 1000) {


            @Override
            public void onTick(long time) {
                SharedPreferences read = getSharedPreferences("Leaving", MODE_PRIVATE);
                String text = read.getString("left", "No name defined");



                if(text.equals("true")){
                    notificationCaller();
                    Toast.makeText(MyService.this, ("Get back on task!"), Toast.LENGTH_LONG).show();

                }
                if(ms > 0){
                    ms -= 1000;
                    Log.i(TAG, String.valueOf(ms));
                    updateTimer(ms);
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    public void updateTimer(long x){
        SharedPreferences.Editor edit = getSharedPreferences("Service", MODE_PRIVATE).edit();
        edit.putLong("Service",x);
        edit.apply();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return START_STICKY;
    }

    @Override
    public void onDestroy(){

    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }


}
