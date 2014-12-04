package com.group2.whatsup.Managers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Location.LatLon;

public class GPSManager extends BaseManager{
    //region Singleton Crap
    private static GPSManager _instance;
    public static void Initialize(Context c){
        if(_instance == null) _instance = new GPSManager(c);
    }
    public static GPSManager Instance(){
        return _instance;
    }
    //endregion

    private LatLon _currentLocation;
    private LocationManager _locMgr;
    private Handler _handler;
    private LocationListener _updateListener;
    private final static int SECONDS_BETWEEN_GPS_POLLS = 60;
    private final Runnable _reenableGps = new Runnable(){

        @Override
        public void run() {
            _locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, _updateListener);
        }
    };


    private GPSManager(Context c){
        super(c);
        _locMgr = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        _handler = new Handler();

        //region Update Listener Instantiation.
        _updateListener = new LocationListener(){
            @Override
            public void onLocationChanged(Location location) {
                LatLon newLoc = new LatLon(location.getLatitude(), location.getLongitude());
                _currentLocation = newLoc;
                _locMgr.removeUpdates(_updateListener);
                _handler.postDelayed(_reenableGps, SECONDS_BETWEEN_GPS_POLLS * 1000);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        //endregion

        _locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, _updateListener);
    }


    public boolean HasLocation(){
        return _currentLocation != null;
    }

    public LatLon CurrentLocation(){
        return _currentLocation;
    }

}
