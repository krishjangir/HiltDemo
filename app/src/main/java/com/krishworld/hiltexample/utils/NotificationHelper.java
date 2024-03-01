package com.krishworld.hiltexample.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.krishworld.hiltexample.R;

public class NotificationHelper {

    private static final String CHANNEL_ID = "my_channel_id";
    private static final String CHANNEL_NAME = "My Channel";

    public static void showMultilineNotification(Context context, String title, String content) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Create a custom notification layout using RemoteViews
        RemoteViews customNotificationView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        customNotificationView.setTextViewText(R.id.custom_notification_title, title);
        customNotificationView.setTextViewText(R.id.custom_notification_content, content);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                //.setContentTitle(title)
                //.setContentText(content)
                .setCustomContentView(customNotificationView)
                /*.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(content))*/ // Set the multiline content here
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Show the notification
        notificationManager.notify(0, builder.build());
    }
}
