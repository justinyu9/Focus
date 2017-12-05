package cmps121.focus;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Start extends AppCompatActivity {
    public String temp2 = "";
    public CountDownTimer countDownTimer;
    public String[] inputarray;
    public long ms;
    public long temp;
    public MyService mService;
    public Random rand = new Random();
    final String pokemon = "pokemon0";
    public StringBuffer sb = new StringBuffer(pokemon);

    public Collections collections = new Collections();


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

    public void tester(View view){
        Toast.makeText(Start.this, "Finished!", Toast.LENGTH_LONG).show();
        SharedPreferences read = getSharedPreferences("taskAtHand", MODE_PRIVATE);
        String text = read.getString("taskAtHand", "No name defined");
        SharedPreferences.Editor e2 = getSharedPreferences(text, MODE_PRIVATE).edit();
        e2.putString("deleted", "true");
        e2.apply();

        read = getSharedPreferences("ClickedStart", MODE_PRIVATE);
        text = read.getString("start", "No name defined");


//        if(text.equals("true")){
//            StringBuilder text2 = new StringBuilder();
////        ArrayList<String> taskList = (ArrayList<String>) getIntent().getSerializableExtra("list");
////        String id = (String) getIntent().getSerializableExtra("taskName");
////        SharedPreferences reader = getSharedPreferences(id, MODE_PRIVATE);
////        taskList.add(id);
////        String[] tasks = new String[taskList.size()];
////        tasks = taskList.toArray(tasks);
//
//            try {
//                // open the file for reading we have to surround it with a try
//
//                InputStream inStream = openFileInput("Sorting.txt");//open the text file for reading
//
//                // if file the available for reading
//                if (inStream != null) {
//
//                    // prepare the file for reading
//                    InputStreamReader inputReader = new InputStreamReader(inStream);
//                    BufferedReader buffReader = new BufferedReader(inputReader);
//
//                    String line = "";
//                    //We initialize a string "line"
//
//                    while (( line = buffReader.readLine()) != null) {
//                        //buffered reader reads only one line at a time, hence we give a while loop to read all till the text is null
//                        //listArray.add(line);
//                        read = getSharedPreferences(line, MODE_PRIVATE);
//                        String compare = read.getString("deleted", "No named defined");
//                        if(compare.equals("true")){
//
//                        }
//                        else {
//                            text2.append(line);
//                            //to display the text in text line
//                            text2.append("@#");
//                        }
//
//                    }
//                }}
//
//            //now we have to surround it with a catch statement for exceptions
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            String last = text.toString();
//            String[] tasks = last.split("@#");
//
//            read = getSharedPreferences(tasks[0], MODE_PRIVATE);
//            SharedPreferences.Editor taskAtHand = getSharedPreferences("taskAtHand", MODE_PRIVATE).edit();
//            taskAtHand.putString("taskAtHand", tasks[0]);
//            text = read.getString("time", "No name defined");
//            taskAtHand.putString("time", text);
//            taskAtHand.apply();
//
//            stopService(new Intent(Start.this, MyService.class));
//            startService(new Intent(Start.this, MyService.class));
//
//
//            Intent intent = new Intent(Start.this, Start.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            Bundle bundle = new Bundle();
//            intent.putExtras(bundle);
//            startActivity(intent);
//
//        }
//        else {
            Intent intent = new Intent(Start.this, Tasks.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        }
//    }

    public void goToCollections(View v){
        Intent intent = new Intent(this, Collections.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences.Editor e2 = getSharedPreferences("Leaving", MODE_PRIVATE).edit();
        e2.putString("left", "false");
        e2.apply();

        SharedPreferences read = getSharedPreferences("taskAtHand", MODE_PRIVATE);
        String text = read.getString("taskAtHand", "No name defined");
        String time = read.getString("time", "No name defined");
        //Toast.makeText(this, String.valueOf(mService.getTime()), Toast.LENGTH_LONG).show();

        TextView taskNameTextView = findViewById(R.id.taskName);
        taskNameTextView.setText(text);
        TextView timerTextView = findViewById(R.id.time);
        timerTextView.setText(time);

        final float timeInt = Float.valueOf(time.split(" ")[0]);

        String hour = time.split(" ")[1];

        if(hour.equals("minutes")){
            ms = (long) (timeInt * 60000);
            temp = (long) (timeInt * 60000);
        }
        else {
            ms = (long) (timeInt * 3600000);
            temp = (long) (timeInt * 3600000);
        }
        Toast.makeText(Start.this, "ms: " + String.valueOf(ms), Toast.LENGTH_LONG).show();
        final ProgressBar countdownProgress = findViewById(R.id.progressBar);
        countdownProgress.setMax((int) temp);
        //ms = getTime();
        final Button myButton = (Button) findViewById(R.id.finished);
        myButton.setEnabled(false);

        countDownTimer = new CountDownTimer(ms, 1000) {
            SharedPreferences read = getSharedPreferences("Leaving", MODE_PRIVATE);
            String text = read.getString("left", "No name defined");

            @Override
            public void onTick(long time) {
                if(ms%5000 ==0){
                    if(text.equals(true)){
                        notificationCaller();
                    }
                }
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
                    myButton.setAlpha(1.0f);
                    myButton.setEnabled(true);
                    onFinish();
                    countDownTimer.cancel();
                }

            }

                @Override
                public void onFinish() {
                    ms = 0;
                    updateTimer();
                    countdownProgress.setProgress((int) ms);
                    ImageView pokemonUpdate = findViewById(R.id.egg_pokemon);
                    int n = rand.nextInt(3) + 1;
                    sb.append(String.valueOf(n));
                    int id = getResources().getIdentifier(sb.toString(), "drawable", getPackageName());
                    pokemonUpdate.setImageResource(id);
                    //collections.obtainData(sb.toString());
                    Toast.makeText(Start.this, "Pokemon added to Collections!", Toast.LENGTH_LONG).show();
                }
            }.start();

        }




    public long getTime(){
        SharedPreferences reader = getSharedPreferences("Service", MODE_PRIVATE);
        long time = reader.getLong("Service", 0);
        return time;
    }
    public void updateTimer(){
        TextView timerView = findViewById(R.id.timerView);
        int hour = (int) ms / 3600000;
        int minutes = (int) (ms-(hour*3600000)) / 60000;
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
        SharedPreferences.Editor e2 = getSharedPreferences("Leaving", MODE_PRIVATE).edit();
        e2.putString("left", "true");
        e2.apply();
        notificationCaller();
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor e2 = getSharedPreferences("Leaving", MODE_PRIVATE).edit();
        e2.putString("left", "true");
        e2.apply();
        notificationCaller();
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences.Editor e2 = getSharedPreferences("Leaving", MODE_PRIVATE).edit();
        e2.putString("left", "false");
        e2.apply();

    }
    public void startService(View view){
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
    }
    public void stopService(View view){
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
