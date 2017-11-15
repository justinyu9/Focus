package cmps121.focus;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;

public class Start extends AppCompatActivity {
    public void notificationCaller() {
        Intent intent = new Intent(this, Start.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        String text = (String) getIntent().getSerializableExtra("time");
//        String taskName = (String) getIntent().getSerializableExtra("taskName");
//        TextView taskNameTextView = findViewById(R.id.taskName);
//        TextView timerTextView = findViewById(R.id.time);
//        timerTextView.setText(text);
//        taskNameTextView.setText(taskName);
        SharedPreferences read = getSharedPreferences("taskAtHand", MODE_PRIVATE);
        String text = read.getString("taskAtHand", "No name defined");
        String time = read.getString("time", "No name defined");
        TextView taskNameTextView = findViewById(R.id.taskName);
        taskNameTextView.setText(text);
        TextView timerTextView = findViewById(R.id.time);
        timerTextView.setText(time);
    }

    @Override
    protected void onPause(){
        super.onPause();

        notificationCaller();
    }
}
