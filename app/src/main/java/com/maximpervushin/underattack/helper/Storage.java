package com.maximpervushin.underattack.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

/**
 * Created by maximpervushin on 01/07/2017.
 */

public class Storage {

    private Storage() {
        super();
    }

    public static String getClientId(Context context) {
        SharedPreferences settings = context.getSharedPreferences("DrwPrefs", 0);
        String clientId = settings.getString("clientId", null);
        if (clientId == null) {
            clientId = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("clientId", clientId);
            editor.commit();
        }
        return clientId;
    }

    public static void setGcmToken(Context context, String token) {
        SharedPreferences settings = context.getSharedPreferences("DrwPrefs", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("gcmToken", token);
        editor.commit();
    }

    public static String getGcmToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences("DrwPrefs", 0);
        return settings.getString("gcmToken", null);
    }
}
