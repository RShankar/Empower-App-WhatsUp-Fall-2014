package edu.fau.whatsup.Managers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import edu.fau.whatsup.Debug.Log;
import edu.fau.whatsup.Entities.Authentication.User;
import edu.fau.whatsup.Entities.Location.LatLon;
import edu.fau.whatsup.Managers.Entities.UserManager;

import java.util.ArrayList;

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
    private ArrayList<Runnable> _waitingCallbacks;
    private final static int SECONDS_BETWEEN_GPS_POLLS = 60;
    private boolean _hasBeenOverridden;
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
        _waitingCallbacks = new ArrayList<Runnable>();
        _hasBeenOverridden = false;
        //region Update Listener Instantiation.
        _updateListener = new LocationListener(){
            @Override
            public void onLocationChanged(Location location) {
                Log.Info("Updating location with Lat & Lon {0}, {1}", location.getLatitude(), location.getLongitude());
                _hasBeenOverridden = false;
                final LatLon newLoc = new LatLon(location.getLatitude(), location.getLongitude());

                //region Update logic to run on new location found.
                Runnable updateLogic = new Runnable() {
                    @Override
                    public void run() {
                        _currentLocation = newLoc;

                        notifyAwaitingCallbacks();

                        //region Updating the user's last known location if necessary
                        User activeUser = UserManager.Instance().GetActiveUser();
                        if(activeUser != null){
                            activeUser.set_lastKnownLocation(_currentLocation);
                            UserManager.Instance().SaveInThread(activeUser);
                        }
                        //endregion
                    }
                };
                //endregion

                //Starting state.
                if(_currentLocation == null){
                    updateLogic.run();
                }
                else{
                    if(!newLoc.equals(_currentLocation)){
                        updateLogic.run();
                    }
                    else{
                        Log.Info("Same location as before. Ignoring update.");
                    }
                }

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

    private void notifyAwaitingCallbacks(){
        if(_waitingCallbacks != null){
            for(Runnable r : _waitingCallbacks){
                r.run();
            }
        }

        _waitingCallbacks = null;
    }

    public boolean HasLocation(){
        return _currentLocation != null;
    }

    public LatLon CurrentLocation(){
        return _currentLocation;
    }

    public void OverrideCurrentLocation(LatLon loc){
        _currentLocation = loc;
        _hasBeenOverridden = true;
    }

    public void WhenLocationSet(Runnable r){
        if(_waitingCallbacks == null){
            _waitingCallbacks = new ArrayList<Runnable>();
        }
        _waitingCallbacks.add(r);
    }

}
