package com.maximpervushin.underattack.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

/**
 * Created by maximpervushin on 01/07/2017.
 */

public class ClientIdHelper {

    private ClientIdHelper() {
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
}
