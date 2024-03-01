package com.krishworld.hiltexample.utils;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showMultilineNotification(
                context,
                "Notification",
                "Notification Notification Notification Notification \nNotification Notification Notification Notification Notification Notification Notification"
        );
    }
}
