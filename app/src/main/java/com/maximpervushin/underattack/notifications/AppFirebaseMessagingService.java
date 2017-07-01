package com.maximpervushin.underattack.notifications;

/**
 * Created by maximpervushin on 01/07/2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.maximpervushin.underattack.ui.MapActivity;
import com.maximpervushin.underattack.R;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = AppFirebaseMessagingService.class.getName();
    private static final int REQUEST_CODE_START_ACTIVITY = 610;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification();
    }

    private void showNotification() {
        PendingIntent pendingIntent;
        Intent intent = new Intent(this, MapActivity.class);

        ComponentName componentName = intent.getComponent();
        if (componentName != null) {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(componentName);
            stackBuilder.addNextIntent(intent);
            pendingIntent = stackBuilder.getPendingIntent(REQUEST_CODE_START_ACTIVITY, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        }


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}
