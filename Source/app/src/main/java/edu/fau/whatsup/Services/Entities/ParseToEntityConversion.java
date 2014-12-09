package edu.fau.whatsup.Services.Entities;

import edu.fau.whatsup.Debug.Log;
import edu.fau.whatsup.Entities.Authentication.User;
import edu.fau.whatsup.Entities.Event;
import edu.fau.whatsup.Entities.EventCategory;
import edu.fau.whatsup.Entities.Location.Address;
import edu.fau.whatsup.Entities.Location.LatLon;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParseToEntityConversion {

    //region User Conversions.
    public static User ConvertUser(ParseObject obj){
        User u = new User();

        u.set_age(obj.getInt(ParseMap.User.Age));
        u.set_emailAddress(obj.getString(ParseMap.User.Email));
        u.set_firstName(obj.getString(ParseMap.User.FirstName));
        u.set_lastName(obj.getString(ParseMap.User.LastName));
        u.set_username(obj.getString(ParseMap.User.Username));
        u.set_password(obj.getString(ParseMap.User.Password), false);
        u.set_entityId(obj.getObjectId());

        ParseGeoPoint pgp = obj.getParseGeoPoint(ParseMap.User.LastKnownLocation);
        if(pgp != null){
            u.set_lastKnownLocation(
                    new LatLon(pgp.getLatitude(), pgp.getLongitude())
            );
        }


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
        e.set_title(obj.getString(ParseMap.Event.Title));

        ParseGeoPoint pgp = obj.getParseGeoPoint(ParseMap.Event.Location);
        e.set_location(new LatLon(pgp.getLatitude(), pgp.getLongitude()));

        Address tgtAddress = new Address();
        tgtAddress.StreetLine1 = obj.getString(ParseMap.Address.Line1);
        tgtAddress.StreetLine2 = obj.getString(ParseMap.Address.Line2);
        tgtAddress.City = obj.getString(ParseMap.Address.City);
        tgtAddress.State = obj.getString(ParseMap.Address.State);
        tgtAddress.PostalCode = obj.getString(ParseMap.Address.PostalCode);
        e.set_address(tgtAddress);

        e.set_description(obj.getString(ParseMap.Event.Description));
        e.set_website(obj.getString(ParseMap.Event.Website));
        e.set_phone(obj.getString(ParseMap.Event.Phone));

        Date startTime = new Date();
        startTime.setTime(obj.getLong(ParseMap.Event.StartTime));

        e.set_startTime(startTime);

        ParseObject ownerObj = (ParseObject) obj.get(ParseMap.Event.Owner);
        e.set_owner(ConvertUser(ownerObj));

        e.set_category(EventCategory.valueOf(obj.getString(ParseMap.Event.Category)));

        try{
            ArrayList<ParseObject> attendees = (ArrayList<ParseObject>) obj.get(ParseMap.Event.Attendees);
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
