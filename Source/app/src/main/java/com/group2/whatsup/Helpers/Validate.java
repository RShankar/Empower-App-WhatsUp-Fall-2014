package com.group2.whatsup.Helpers;

import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.Location.Address;

public class Validate {


    public static boolean Event(Event e){
        return EventTitle(e.get_title()) &&
                Address(e.get_address());
    }

    public static boolean EventTitle(String title){
        return true;
    }

    //region Address Validation
    public static boolean Address(Address a){
        return AddressLine1(a.StreetLine1) &&
                AddressLine2(a.StreetLine2) &&
                City(a.City) &&
                State(a.State) &&
                PostalCode(a.PostalCode);
    }

    public static boolean AddressLine1(String line1){
        return true;
    }

    public static boolean AddressLine2(String line2){
        return true;
    }

    public static boolean City(String city){
        return true;
    }

    public static boolean State(String state){
        return true;
    }

    public static boolean PostalCode(String postalCode){
        return true;
    }
    //endregion


}
