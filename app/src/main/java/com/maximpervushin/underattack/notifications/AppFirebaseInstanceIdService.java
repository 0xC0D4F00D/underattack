package com.maximpervushin.underattack.notifications;

/**
 * Created by maximpervushin on 01/07/2017.
 */

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;


public class AppFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = AppFirebaseInstanceIdService.class.getName();

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("deviceToken", refreshedToken);
        parameters.put("deviceType", "Android");
        Log.d(TAG, refreshedToken);
//        parameters.put("appVersion", VersionHelper.getVersion(this));
//        HttpHelper.post(this, "http://promurom.ru/api/registration/", parameters, new Callback<Result<String>>() {
//            @Override
//            public void onResponse(Result<String> response) {
//                if (response.success) {
//                    Log.d(TAG, "SUCCESS");
//                } else {
//                    Log.d(TAG, "FAILURE");
//                }
//            }
//        });
    }
}
