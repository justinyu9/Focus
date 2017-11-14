package cmps121.focus;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Alston-PC on 11/10/2017.
 */

public class NotficationHelper extends ContextWrapper {
    public static final String channel00= "channel00";
    public NotificationManager nManager;
    public NotficationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel();
    }

    public void createChannel(){
        NotificationChannel channel = new NotificationChannel(channel00, "channelName", NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getNotificationManager().createNotificationChannel(channel);
    }

    public NotificationManager getNotificationManager(){
        nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return nManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message){
        return  (android.support.v7.app.NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(message)
                .setChannelId(channel00)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher_round);
    }
}
