package com.group2.whatsup.Services.Entities;

import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Authentication.User;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.EventCategory;
import com.group2.whatsup.Entities.Location.Address;
import com.group2.whatsup.Entities.Location.LatLon;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParseToEntityConversion {

    //region User Conversions.
    public static User ConvertUser(ParseObject obj){
        User u = new User();

        u.set_age(obj.getInt("age"));
        u.set_emailAddress(obj.getString("emailAddress"));
        u.set_firstName(obj.getString("firstName"));
        u.set_lastName(obj.getString("lastName"));
        u.set_username(obj.getString("userName"));
        u.set_password(obj.getString("passWord"), false);
        u.set_entityId(obj.getObjectId());

        return u;
    }
    public static ArrayList<User> ConvertUsers(List<ParseObject> obj){
        ArrayList<User> retVal = new ArrayList<User>();

        if(obj != null){
            for(ParseObject p : obj){
                retVal.add(ConvertUser(p));
            }
        }

        return retVal;
    }
    //endregion

    //region Event Conversions
    public static Event ConvertEvent(ParseObject obj){
        Event e = new Event();

        e.set_entityId(obj.getObjectId());
        e.set_title(obj.getString("title"));

        ParseGeoPoint pgp = obj.getParseGeoPoint("location");
        e.set_location(new LatLon(pgp.getLatitude(), pgp.getLongitude()));

        Address tgtAddress = new Address();
        tgtAddress.StreetLine1 = obj.getString("street1");
        tgtAddress.StreetLine2 = obj.getString("street2");
        tgtAddress.City = obj.getString("city");
        tgtAddress.State = obj.getString("state");
        tgtAddress.PostalCode = obj.getString("postalCode");
        e.set_address(tgtAddress);

        e.set_description(obj.getString("description"));
        e.set_website(obj.getString("website"));

        Date startTime = new Date();
        startTime.setTime(obj.getLong("startTime"));

        Date endTime = new Date();
        endTime.setTime(obj.getLong("endTime"));

        e.set_startTime(startTime);
        e.set_endTime(endTime);

        ParseObject ownerObj = (ParseObject) obj.get("owner");
        e.set_owner(ConvertUser(ownerObj));

        e.set_category(EventCategory.valueOf(obj.getString("category")));

        try{
            ArrayList<ParseObject> attendees = (ArrayList<ParseObject>) obj.get("attendees");
            if(attendees != null){
                ArrayList<User> attendeesToAdd = new ArrayList<User>();
                for(ParseObject user : attendees){
                    attendeesToAdd.add(ConvertUser(user));
                }
                e.set_attendees(attendeesToAdd);
            }
        }
        catch(Exception ex){
            Log.Error("Failed to parse attendees: {0}", ex.getMessage());
        }


        return e;
    }
    public static ArrayList<Event> ConvertEvents(List<ParseObject> objs){
        ArrayList<Event> retVal = new ArrayList<Event>();

        if(objs != null){
            for(ParseObject p : objs){
                retVal.add(ConvertEvent(p));
            }
        }


        return retVal;
    }
    //endregion

}
