package com.maximpervushin.underattack.helper;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.maximpervushin.underattack.data.AlarmsListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by maximpervushin on 01/07/2017.
 */

public class HttpHelper {

//    private static String API_ROOT = "http://darwin.mayfleet.com:80/api/v1";
    private static String API_ROOT = "http://192.168.0.150:8085/api/v1";

    private HttpHelper() {
        super();
    }

    public static void userUpdatePushToken(final Context context) {
        final String pushToken = Storage.getGcmToken(context);
        if (pushToken == null) {
            return;
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                String clientId = Storage.getClientId(context);
                String jsonString = "{\n" +
                        " \"userId\": \"" + clientId + "\",\n" +
                        " \"token\": \"" + pushToken + "\",\n" +
                        " \"platform\": \"android\"\n" +
                        "}";
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonString);
                String url = API_ROOT + "/user/update-push-token";
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void userUpdateLocation(final Context context, final Location location) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                String clientId = Storage.getClientId(context);
                String jsonString = "{\n" +
                        " \"userId\": \"" + clientId + "\",\n" +
                        " \"lat\": " + location.getLatitude() + ",\n" +
                        " \"lon\": " + location.getLongitude() + ",\n" +
                        " \"accuracy\": " + location.getAccuracy() + "\n" +
                        "}";
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonString);
                String url = API_ROOT + "/user/update-location";
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static AlarmsListResponse alarmListV(final Context context) {
        OkHttpClient client = new OkHttpClient();
        String clientId = Storage.getClientId(context);
        Request request = new Request.Builder()
                .url(API_ROOT + "/alarm/list?userId=" + clientId)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
            boolean success = jsonObject.get("success").getAsBoolean();
            List<LatLng> alarms = new ArrayList<>();
            JsonObject result = jsonObject.get("result").getAsJsonObject();
            JsonArray alarmsJsonArray = result.get("alarms").getAsJsonArray();
            for (JsonElement childJsonElement : alarmsJsonArray) {
                LatLng latLng = new LatLng(
                        childJsonElement.getAsJsonObject().get("lat").getAsDouble(),
                        childJsonElement.getAsJsonObject().get("lon").getAsDouble()
                );
                alarms.add(latLng);
            }
            JsonArray bboxJsonArray = result.get("bbox").getAsJsonArray();
            LatLngBounds bbox = new LatLngBounds(
                    new LatLng(
                            bboxJsonArray.get(1).getAsDouble(),
                            bboxJsonArray.get(0).getAsDouble()
                    ),
                    new LatLng(
                            bboxJsonArray.get(3).getAsDouble(),
                            bboxJsonArray.get(2).getAsDouble()
                    )
            );
            AlarmsListResponse alarmsListResponse = new AlarmsListResponse(
                    success,
                    alarms,
                    bbox
            );

            return alarmsListResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void alarmTrigger(final Context context, final Location location) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                String clientId = Storage.getClientId(context);
                String jsonString = "{\n" +
                        " \"userId\": \"" + clientId + "\",\n" +
                        " \"lat\": " + location.getLatitude() + ",\n" +
                        " \"lon\": " + location.getLongitude() + ",\n" +
                        " \"accuracy\": " + location.getAccuracy() + "\n" +
                        "}";
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonString);
                String url = API_ROOT + "/alarm/trigger";
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void alarmCancel(final Context context, final Location location) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                String clientId = Storage.getClientId(context);
                String jsonString = "{\n" +
                        " \"userId\": \"" + clientId + "\"\n" +
//                        " \"lat\": " + location.getLatitude() + ",\n" +
//                        " \"lon\": " + location.getLongitude() + ",\n" +
//                        " \"accuracy\": " + location.getAccuracy() + "\n" +
                        "}";
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonString);
                String url = API_ROOT + "/alarm/cancel";
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

