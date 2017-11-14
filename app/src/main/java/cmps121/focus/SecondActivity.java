package cmps121.focus;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

public class SecondActivity extends AppCompatActivity {
    public void notify(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        String channel00= "channel00";
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channel00, "channelName", NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        nManager.createNotificationChannel(channel);

        android.support.v4.app.NotificationCompat.Builder NB = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Your plant is dying!")
                .setContentText("Click here to save it!")
                .setChannelId(channel00)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setContentIntent(pi);

        nManager.notify(1, NB.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ImageButton myButton = findViewById(R.id.settings);
        myButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(SecondActivity.this, Temp.class);
                //intent.putExtra("Text 2", edit_text_2.getText().toString());
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        CalendarView calendar = findViewById(R.id.calendarView);
        calendar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(SecondActivity.this, Temp.class);
                //intent.putExtra("Text 2", edit_text_2.getText().toString());
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
