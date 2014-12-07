package com.group2.whatsup.Helpers;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Location.Address;
import com.group2.whatsup.Entities.Location.LatLon;
import com.group2.whatsup.Managers.SettingsManager;

import java.util.List;
import java.util.Locale;

public class LocationHelper {

    public static LatLon GetLatLonFromAddress(Address address){

        Geocoder coder = new Geocoder(SettingsManager.Instance().get_context(), Locale.getDefault());

        LatLon result = null;

        try{
            List<android.location.Address> adds = coder.getFromLocationName(address.GeoCoderFriendlyName(), 1);

            for(int i = 0;i < 10; i++){
                if(adds.size() > 0){
                    result = new LatLon(adds.get(0).getLatitude(), adds.get(0).getLongitude());
                    break;
                }

                adds = coder.getFromLocationName(address.GeoCoderFriendlyName(), 1);
            }


        }
        catch(Exception ex){
            Log.Error("Exception occurred attempting to retrieve Latitude & Longitude from address: {0}", ex.getMessage());
        }

        return result;
    }

    public static Address GetAddressFromLatLon(LatLon loc){
        Address result = null;

        try{
            Geocoder coder = new Geocoder(SettingsManager.Instance().get_context(), Locale.getDefault());
            List<android.location.Address> adds = coder.getFromLocation(loc.get_latitude(), loc.get_longitude(), 1);

            for(int i = 0; i < 10; i++){
                if(adds.size() > 0){
                    android.location.Address res = adds.get(0);
                    result = new Address();
                    result.StreetLine1 = res.getThoroughfare();
                    result.StreetLine2 = res.getSubThoroughfare();
                    result.City = res.getLocality();
                    result.State = res.getAdminArea();
                    result.PostalCode = res.getPostalCode();
                    break;
                }

                adds = coder.getFromLocation(loc.get_latitude(), loc.get_longitude(), 1);
            }
        }
        catch(Exception ex){
            Log.Error("Exception occurred attempting to retrieve address from Lat & Lon : {0}", ex.getMessage());
        }

        return result;
    }
}
