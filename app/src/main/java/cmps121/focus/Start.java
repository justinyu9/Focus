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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import java.util.concurrent.ExecutionException;

import cmps121.focus.pokeapi.PokemonDatabase;

public class Start extends AppCompatActivity {
    public CountDownTimer countDownTimer;
    public long ms;
    public long temp;
    public MyService mService;
    public Random rand = new Random();
    final String pokemon = "pokemon0";
    public StringBuffer sb = new StringBuffer(pokemon);
    public PokemonDatabase pokemonDatabase;
    public Collections collections;
    public int count = 0;


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
        SharedPreferences.Editor e = getSharedPreferences(text, MODE_PRIVATE).edit();
        e.putString("deleted", "true");
        e.apply();
        Intent intent = new Intent(Start.this, Tasks.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void goToCollections(View v){
        Intent intent = new Intent(this, Collections.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        collections = new Collections();
        pokemonDatabase = new PokemonDatabase(this);

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
            ms = (long) timeInt * 60000;
            temp = (long) timeInt * 60000;
        }
        else {
            ms = (long) timeInt * 3600000;
            temp = (long) timeInt * 3600000;
        }
        final ProgressBar countdownProgress = findViewById(R.id.progressBar);
        countdownProgress.setMax((int) temp);
        ms = getTime();
        final Button myButton = (Button) findViewById(R.id.finished);
        myButton.setEnabled(false);

            countDownTimer = new CountDownTimer(ms, 1000) {


                @Override
                public void onTick(long time) {
                    count++;
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
                    if(count == 4){
                        count = 0;
                        Animation wobble = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wobble);
                        ImageView pokemonUpdate = findViewById(R.id.egg_pokemon);
                        pokemonUpdate.startAnimation(wobble);
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
                    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                    ms = 0;
                    updateTimer();
                    countdownProgress.setProgress((int)ms);
                    ImageView pokemonUpdate = findViewById(R.id.egg_pokemon);
                    int id = rand.nextInt(150)+1;
                    pokemonUpdate.setBackgroundResource(R.drawable.pokemon_gradient_circle);
                    Glide.with(getApplicationContext()).load("http://pokeapi.co/media/sprites/pokemon/" + String.valueOf(id) + ".png").crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).override(500,500).into(pokemonUpdate);
                    Pokemon p = null;
                    try {
                        p = collections.getPokemon(String.valueOf(id));
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(p!=null){
                        Log.i("POKEMON", p.getAttack());
                        pokemonDatabase.insertData(p.getName(), String.valueOf(p.getID()), p.getHp(), p.getAttack(), p.getDefense(), p.getHeight(), p.getWeight());
                    }
//                    if(getSharedPreferences("PokemonSharedPreferences", MODE_PRIVATE) == null){
//                        SharedPreferences.Editor SFE = getSharedPreferences("PokemonSharedPreferences", MODE_PRIVATE).edit();
//                        JSONArray pokeJsonArray = new JSONArray();
//                        JSONObject pokeJsonObject = new JSONObject();
//                        try {
//                            pokeJsonObject.put("name", p.getName());
//                            pokeJsonObject.put("id", p.getNumber());
//                            pokeJsonArray.put(pokeJsonObject);
//                            SFE.putString("pokeJsonString", pokeJsonArray.toString());
//                            SFE.commit();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else{
//                        SharedPreferences pokemonSharedPreferences = getSharedPreferences("PokemonSharedPreferences", MODE_PRIVATE);
//                        String pokeJsonString = pokemonSharedPreferences.getString("pokeJsonString", "");
//                        try {
//                            JSONArray pokeJsonArray = new JSONArray(pokeJsonString);
//                            JSONObject pokeJsonObject = new JSONObject();
//                            pokeJsonObject.put("name", p.getName());
//                            pokeJsonObject.put("id", p.getNumber());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    //collections.obtainData(sb.toString());
                    Toast.makeText(Start.this, p.getName() + " has been added to your collections!", Toast.LENGTH_LONG).show();
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
