package com.maximpervushin.underattack.helper;

import com.maximpervushin.underattack.data.AlarmsListResponse;

public interface OnAlarmListLoadedCallback {
    void onMapReady(AlarmsListResponse alarmsListResponse);
}
