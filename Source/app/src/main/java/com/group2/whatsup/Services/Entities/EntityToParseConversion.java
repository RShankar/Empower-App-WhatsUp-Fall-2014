package com.group2.whatsup.Services.Entities;

import com.group2.whatsup.Entities.Authentication.User;
import com.group2.whatsup.Entities.BaseEntity;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.Location.Address;
import com.group2.whatsup.Entities.Location.LatLon;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.ArrayList;

public class EntityToParseConversion {

    public static ParseObject BaseEntityToParseObject(BaseEntity be){
        ParseObject obj = new ParseObject(be.getEntityName());
        if(be.get_entityId() != null) obj.setObjectId(be.get_entityId());
        return obj;
    }

    public static ParseObject UserToParseObject(User u){
        ParseObject obj = BaseEntityToParseObject(u);

        obj.put(ParseMap.User.Username, u.get_username());
        obj.put(ParseMap.User.Password, u.get_password());
        obj.put(ParseMap.User.Age, u.get_age());
        obj.put(ParseMap.User.Email, u.get_emailAddress());
        obj.put(ParseMap.User.FirstName, u.get_firstName());
        obj.put(ParseMap.User.LastName, u.get_lastName());

        LatLon lastKnown = u.get_lastKnownLocation();
        if(lastKnown != null){
            ParseGeoPoint pgp = new ParseGeoPoint(lastKnown.get_latitude(), lastKnown.get_longitude());
            obj.put(ParseMap.User.LastKnownLocation, pgp);
        }


        return obj;
    }

    public static ParseObject EventToParseObject(Event e){
        ParseObject obj = BaseEntityToParseObject(e);

        obj.put(ParseMap.Event.Title, e.get_title());

        ParseGeoPoint pgp = new ParseGeoPoint(e.get_location().get_latitude(), e.get_location().get_longitude());
        obj.put(ParseMap.Event.Location, pgp);

        Address tgtAddress = e.get_address();
        if(tgtAddress != null){
            if(tgtAddress.StreetLine1 != null) obj.put(ParseMap.Address.Line1, tgtAddress.StreetLine1);
            if(tgtAddress.StreetLine2 != null) obj.put(ParseMap.Address.Line2, tgtAddress.StreetLine2);
            if(tgtAddress.City != null) obj.put(ParseMap.Address.City, tgtAddress.City);
            if(tgtAddress.State != null) obj.put(ParseMap.Address.State, tgtAddress.State);
            if(tgtAddress.PostalCode != null) obj.put(ParseMap.Address.PostalCode, tgtAddress.PostalCode);
        }

        obj.put(ParseMap.Event.Description, e.get_description());
        obj.put(ParseMap.Event.Website, e.get_website());
        obj.put(ParseMap.Event.StartTime, e.get_startTime().getTime());
        obj.put(ParseMap.Event.EndTime, e.get_endTime().getTime());
        obj.put(ParseMap.Event.Category, e.get_category_name());
        obj.put(ParseMap.Event.Phone, e.get_phone());

        ParseObject userObj = new ParseObject(User.ENTITY_NAME);
        userObj.setObjectId(e.get_owner().get_entityId());
        obj.put(ParseMap.Event.Owner, userObj);

        ArrayList<ParseObject> attendeesObj = new ArrayList<ParseObject>();
        ArrayList<User> attendees = e.get_attendees();
        if(attendees.size() > 0){
            for(User u : attendees){
                ParseObject temp = new ParseObject(User.ENTITY_NAME);
                temp.setObjectId(u.get_entityId());
                attendeesObj.add(temp);
            }
        }
        obj.put(ParseMap.Event.Attendees, attendeesObj);


        return obj;
    }
}
