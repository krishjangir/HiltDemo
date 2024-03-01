package com.krishworld.hiltexample.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

public class NotificationScheduler {
    public static void scheduleNotification(Context context, long delayInMillis) {
        // Create a unique ID for the PendingIntent
        int notificationId = 1;

        // Create an Intent to trigger the NotificationReceiver
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("notificationId", notificationId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        // Calculate the time when the notification should be displayed
        long futureInMillis = System.currentTimeMillis() + delayInMillis;

        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Schedule the notification
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }
}
