package com.maximpervushin.underattack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by maximpervushin on 01/07/2017.
 */

public class DrwBootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent locationServiceIntent = new Intent(context, DrwLocationService.class);
        locationServiceIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        context.startService(locationServiceIntent);
    }
}
