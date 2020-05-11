package com.example.chatandroid.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.example.chatandroid.R;

public class NotificationUtil {

    public static String CHANEL_ID = "ChatAndroid";
    public static String CHANEL_NAME = "Message";
    public static int CHANEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
    public static int consecutive = 1;

    public static void createNotification(Context context, String message){

        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationChannel chanel = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            chanel = new NotificationChannel(CHANEL_ID,CHANEL_NAME,CHANEL_IMPORTANCE);
            manager.createNotificationChannel(chanel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANEL_ID)
                .setContentTitle("New Message").setContentText(message).setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(consecutive,builder.build());
        consecutive++;
    }
}
