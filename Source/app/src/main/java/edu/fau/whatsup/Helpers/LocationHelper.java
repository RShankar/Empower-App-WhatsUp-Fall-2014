package edu.fau.whatsup.Helpers;

import android.content.Intent;
import android.location.Geocoder;
import android.net.Uri;

import edu.fau.whatsup.Debug.Log;
import edu.fau.whatsup.Entities.Location.Address;
import edu.fau.whatsup.Entities.Location.LatLon;
import edu.fau.whatsup.Managers.SettingsManager;

import java.text.MessageFormat;
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

    public static Intent GetGoogleMapsIntent(LatLon src, LatLon dest){
        String uri = MessageFormat.format("http://maps.google.com/maps?saddr={0},{1}&daddr={2},{3}", src.get_latitude(), src.get_longitude(), dest.get_latitude(), dest.get_longitude());
        Intent navIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        return navIntent;
    }
}
