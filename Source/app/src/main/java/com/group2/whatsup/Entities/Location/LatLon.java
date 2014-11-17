package com.group2.whatsup.Entities.Location;

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
}
