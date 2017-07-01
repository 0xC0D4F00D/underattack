package com.maximpervushin.underattack.data;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

/**
 * Created by maximpervushin on 01/07/2017.
 */

public class AlarmsListResponse {

    public boolean success;
    public List<LatLng> alarms;
    public LatLngBounds bbox;

    public AlarmsListResponse(boolean success, List<LatLng> alarms, LatLngBounds bbox) {
        this.success = success;
        this.alarms = alarms;
        this.bbox = bbox;
    }
}
