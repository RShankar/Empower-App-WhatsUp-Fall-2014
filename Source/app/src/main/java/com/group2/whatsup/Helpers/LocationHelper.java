package com.group2.whatsup.Helpers;

import android.location.Geocoder;

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
}
