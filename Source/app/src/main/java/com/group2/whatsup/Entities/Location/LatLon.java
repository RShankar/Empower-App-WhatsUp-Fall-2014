package com.group2.whatsup.Entities.Location;

import com.google.android.gms.maps.model.LatLng;
import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Helpers.MathHelpers;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;

public class LatLon {
    private double _latitude;
    private double _longitude;

    public LatLon(double lat, double lon){
        _latitude = lat;
        _longitude = lon;
    }

    public double get_latitude(){
        return _latitude;
    }

    public double get_longitude(){
        return _longitude;
    }

    public LatLng get_LatLng() {
        LatLng LL = new LatLng(_latitude, _longitude);
        return LL;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof LatLon){
            LatLon other = (LatLon) o;
            double otherLat = MathHelpers.Round(other.get_latitude(), 3);
            double otherLon = MathHelpers.Round(other.get_longitude(), 3);
            double thisLat = MathHelpers.Round(get_latitude(), 3);
            double thisLon = MathHelpers.Round(get_longitude(), 3);
            return otherLat == thisLat && otherLon == thisLon;
        }

        return super.equals(o);
    }

    @Override
    public String toString(){
        return MessageFormat.format("[Lat:{0}],[Lon:{1}", get_latitude(), get_longitude());
    }
}
