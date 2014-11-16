package com.group2.whatsup.Managers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

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

    private GPSManager(Context c){
        super(c);
        _locMgr = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        _locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLon newLoc = new LatLon(location.getLatitude(), location.getLongitude());
                _currentLocation = newLoc;
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
        });
    }


    public boolean HasLocation(){
        return _currentLocation != null;
    }

    public LatLon CurrentLocation(){
        return _currentLocation;
    }

}
