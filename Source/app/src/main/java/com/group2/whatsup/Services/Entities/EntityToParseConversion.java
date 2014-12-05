package com.group2.whatsup.Services.Entities;

import com.group2.whatsup.Entities.Authentication.User;
import com.group2.whatsup.Entities.BaseEntity;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.Location.Address;
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

        obj.put("userName", u.get_username());
        obj.put("passWord", u.get_password());
        obj.put("age", u.get_age());
        obj.put("emailAddress", u.get_emailAddress());
        obj.put("firstName", u.get_firstName());
        obj.put("lastName", u.get_lastName());


        return obj;
    }

    public static ParseObject EventToParseObject(Event e){
        ParseObject obj = BaseEntityToParseObject(e);

        obj.put("title", e.get_title());

        ParseGeoPoint pgp = new ParseGeoPoint(e.get_location().get_latitude(), e.get_location().get_longitude());
        obj.put("location", pgp);

        Address tgtAddress = e.get_address();
        if(tgtAddress != null){
            if(tgtAddress.StreetLine1 != null) obj.put("street1", tgtAddress.StreetLine1);
            if(tgtAddress.StreetLine2 != null) obj.put("street2", tgtAddress.StreetLine2);
            if(tgtAddress.City != null) obj.put("city", tgtAddress.City);
            if(tgtAddress.State != null) obj.put("state", tgtAddress.State);
            if(tgtAddress.PostalCode != null) obj.put("postalCode", tgtAddress.PostalCode);
        }

        obj.put("description", e.get_description());
        obj.put("website", e.get_website());
        obj.put("startTime", e.get_startTime().getTime());
        obj.put("endTime", e.get_endTime().getTime());
        obj.put("category", e.get_category_name());

        ParseObject userObj = new ParseObject(User.ENTITY_NAME);
        userObj.setObjectId(e.get_owner().get_entityId());
        obj.put("owner", userObj);

        ArrayList<ParseObject> attendeesObj = new ArrayList<ParseObject>();
        ArrayList<User> attendees = e.get_attendees();
        if(attendees.size() > 0){
            for(User u : attendees){
                ParseObject temp = new ParseObject(User.ENTITY_NAME);
                temp.setObjectId(u.get_entityId());
                attendeesObj.add(temp);
            }
        }
        obj.put("attendees", attendeesObj);


        return obj;
    }
}
