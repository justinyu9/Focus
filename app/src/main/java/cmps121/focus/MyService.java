package cmps121.focus;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by justin on 11/15/2017.
 */

public class MyService extends Service {
    public CountDownTimer countDownTimer;


    public long ms;
    public long temp;
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences read = getSharedPreferences("taskAtHand", MODE_PRIVATE);
        String text = read.getString("taskAtHand", "No name defined");
        String time = read.getString("time", "No name defined");
        final float timeInt = Float.valueOf(time.split(" ")[0]);
        String hour = time.split(" ")[1];

        if (hour.equals("minutes")) {
            ms = (long) timeInt * 60000;
            temp = (long) timeInt * 60000;
        } else {
            ms = (long) timeInt * 3600000;
            temp = (long) timeInt * 3600000;
        }
        Toast.makeText(this, String.valueOf(ms), Toast.LENGTH_LONG).show();

        Toast.makeText(this, String.valueOf(temp), Toast.LENGTH_LONG).show();
        countDownTimer = new CountDownTimer(ms, 1000) {
            @Override
            public void onTick(long time) {
                if(ms > 0){
                    ms -= 1000;
                    Log.i(TAG, String.valueOf(ms));
                }
            }
            public long getTime(){
                return ms;
            }
            @Override
            public void onFinish() {

            }
        }.start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        Toast.makeText(this, "Service Ended", Toast.LENGTH_LONG).show();

    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

}
