package com.maximpervushin.underattack.notifications;

/**
 * Created by maximpervushin on 01/07/2017.
 */

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.maximpervushin.underattack.helper.HttpHelper;


public class AppFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = AppFirebaseInstanceIdService.class.getName();

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, refreshedToken);
        HttpHelper.userUpdatePushToken(this, refreshedToken);
    }
}
