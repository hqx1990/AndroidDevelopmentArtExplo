package com.bestvike.androiddevelopmentartexploration.liveData;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.MainThread;
import android.util.Log;

public class LocationLiveData extends LiveData<Location> {

    private static LocationLiveData sInstance;
    private LocationManager locationManager;

    @MainThread
    public static LocationLiveData get(Context context) {
        if (sInstance == null) {
            sInstance = new LocationLiveData(context.getApplicationContext());
        }
        return sInstance;
    }

//    private SimpleLocationListener listener = new SimpleLocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            setValue(location);
//        }
//    };

    private LocationLiveData(Context context) {
        locationManager = (LocationManager) context.getSystemService(
                Context.LOCATION_SERVICE);
    }

    @Override
    protected void onActive() {
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        Log.e("liveData","测试：回到前台");
    }

    @Override
    protected void onInactive() {
        Log.e("liveData","测试：去后台");
//        locationManager.removeUpdates(listener);
    }
}
