package com.maximpervushin.underattack.ui;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maximpervushin.underattack.R;
import com.maximpervushin.underattack.data.AlarmsListResponse;
import com.maximpervushin.underattack.helper.HttpHelper;

/**
 * Created by maximpervushin on 01/07/2017.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    AlarmsListResponse alarmsListResponse;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MapActivity.this.alarmsListResponse = HttpHelper.alarmListV(MapActivity.this);
                MapActivity.this.reloadAlarms();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        reloadAlarms();
    }

    private void reloadAlarms() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (googleMap != null && alarmsListResponse != null) {
                    for (LatLng latLng : alarmsListResponse.alarms) {
                        googleMap.addMarker(new MarkerOptions().position(latLng).title("Beat here"));
                    }
                    Resources r = getResources();
                    int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(alarmsListResponse.bbox, px));
                }
            }
        });
    }
}

