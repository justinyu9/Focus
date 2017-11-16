package cmps121.focus;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Date;
import java.util.Random;

public class Start extends AppCompatActivity {
    public CountDownTimer countDownTimer;
    public long ms;
    public long temp;
    public Random rand = new Random();
    final String pokemon = "pokemon0";
    public StringBuffer sb = new StringBuffer(pokemon);

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
    public void skip5(View view){
        ms -= 300000;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences read = getSharedPreferences("taskAtHand", MODE_PRIVATE);
        String text = read.getString("taskAtHand", "No name defined");
        String time = read.getString("time", "No name defined");

        TextView taskNameTextView = findViewById(R.id.taskName);
        taskNameTextView.setText(text);
        TextView timerTextView = findViewById(R.id.time);
        timerTextView.setText(time);

        final float timeInt = Float.valueOf(time.split(" ")[0]);
        String hour = time.split(" ")[1];

        if(hour.equals("minutes")){
            ms = (long) timeInt * 60000;
            temp = (long) timeInt * 60000;
        }
        else {
            ms = (long) timeInt * 3600000;
            temp = (long) timeInt * 3600000;
        }
        final ProgressBar countdownProgress = findViewById(R.id.progressBar);
        countdownProgress.setMax((int) temp);
            countDownTimer = new CountDownTimer(ms, 1000) {
                @Override
                public void onTick(long time) {
                    if(ms < temp/4 && ms > 0){
                        ImageView pokemonUpdate = findViewById(R.id.egg_pokemon);
                        pokemonUpdate.setImageResource(R.drawable.pokemon_egg_crack_3);
                    }
                    else if(ms < temp - temp/2 && ms > 0){
                        ImageView pokemonUpdate = findViewById(R.id.egg_pokemon);
                        pokemonUpdate.setImageResource(R.drawable.pokemon_egg_crack_2);
                    }
                    else if(ms < temp - temp/4 && ms > 0){
                        ImageView pokemonUpdate = findViewById(R.id.egg_pokemon);
                        pokemonUpdate.setImageResource(R.drawable.pokemon_egg_crack_1);
                    }
                    if(ms > 0){
                        ms -= 1000;
                        updateTimer();
                        countdownProgress.setProgress((int)ms);
                    }
                    else{
                        onFinish();
                        countDownTimer.cancel();
                    }

                }

                @Override
                public void onFinish() {
                    ms = 0;
                    updateTimer();
                    countdownProgress.setProgress((int)ms);
                    ImageView pokemonUpdate = findViewById(R.id.egg_pokemon);
                    int n = rand.nextInt(3)+1;
                    sb.append(String.valueOf(n));
                    int id = getResources().getIdentifier(sb.toString(), "drawable", getPackageName());
                    pokemonUpdate.setImageResource(id);
                    Toast.makeText(Start.this, "Pokemon added to Collections!", Toast.LENGTH_LONG).show();
                }
            }.start();


    }
    public void updateTimer(){
        TextView timerView = findViewById(R.id.timerView);
        int hour = (int) ms / 3600000;
        int minutes = (int) ms / 60000;
        int seconds = (int) ms % 60000 / 1000;

        String timeLeft = "";
        if(hour < 10)
            timeLeft += "0";
        timeLeft = "" + hour;
        timeLeft += ":";
        if(minutes < 10)
            timeLeft += "0";
        timeLeft += minutes;
        timeLeft += ":";
        if(seconds < 10)
            timeLeft += "0";
        timeLeft += seconds;

        timerView.setText(timeLeft);
    }

    @Override
    protected void onStop(){
        super.onStop();

        notificationCaller();
    }
}
