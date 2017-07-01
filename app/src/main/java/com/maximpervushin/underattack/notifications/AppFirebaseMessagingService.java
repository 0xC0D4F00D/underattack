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
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.maximpervushin.underattack.ui.MapActivity;
import com.maximpervushin.underattack.R;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = AppFirebaseMessagingService.class.getName();
    private static final int REQUEST_CODE_START_ACTIVITY = 610;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        RemoteNotificationItem item = null;
        try {
            String itemId = remoteMessage.getData().get("itemId");
            Log.d(TAG, itemId);

//            if (title != null) {
//                showNotification(title, body != null ? body : "");
//            }

//            if (remoteMessage.getData().containsKey("itemLink")) {
//                item = new RemoteNotificationItem(itemId, title, body, itemType, remoteMessage.getData().get("itemLink"));
//            } else {
//                item = new RemoteNotificationItem(itemId, title, body, itemType, null);
//            }
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

//        if (item != null) {
            showNotification();
//        }

    }

    private void showNotification() {
        PendingIntent pendingIntent;
        Intent intent = new Intent(this, MapActivity.class);
        ComponentName componentName = intent.getComponent();
//        intent.putExtra("Item", identifierItem);
        if (componentName != null) {
            // The stack builder object will contain an artificial back
            // stack for the started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            // Adds the back stack for the Intent (but not the Intent itself) <== This comment must be wrong!
            stackBuilder.addParentStack(componentName);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(intent);

            pendingIntent = stackBuilder.getPendingIntent(REQUEST_CODE_START_ACTIVITY, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        } else {
            pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE_START_ACTIVITY, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Hello")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
//    private void showNotification(@NonNull RemoteNotificationItem item) {
//        PendingIntent pendingIntent;
//        Intent intent = new Intent(this, WebViewActivity.class);
//        intent.putExtra(WebViewActivity.LOGIC_TYPE_EXTRA, LogicDispatcher.getRemoteNotificationLogicType(item));
//        intent.putExtra(WebViewActivity.ITEM_EXTRA, item);
//
//        ComponentName componentName = intent.getComponent();
//        if (componentName != null) {
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//            stackBuilder.addParentStack(componentName);
//            stackBuilder.addNextIntent(intent);
//            pendingIntent = stackBuilder.getPendingIntent(REQUEST_CODE_START_ACTIVITY, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
//        } else {
//            pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
//        }
//
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(item.title)
//                .setContentText(item.body != null ? item.body : "")
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
}
