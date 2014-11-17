package com.group2.whatsup.Services.Entities;

import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.Location.LatLon;
import com.group2.whatsup.Managers.SettingsManager;
import com.group2.whatsup.ServiceContracts.Entities.IEventService;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ParseEventService extends BaseParseService implements IEventService {

    private static Event eventRef = new Event();

    @Override
    public ArrayList<Event> RetrieveEventsNear(LatLon loc) {
        ParseQuery<ParseObject> query = queryFor(Event.ENTITY_NAME);
        ParseGeoPoint targetPoint = new ParseGeoPoint(loc.get_latitude(), loc.get_longitude());
        query.whereWithinMiles("location", targetPoint, SettingsManager.Instance().DistancePreference());

        List<ParseObject> objs = null;
        try{
            objs = query.find();
        }
        catch(ParseException ex){
            Log.Error("Error querying for events near a location: {0}", ex.getMessage());
        }

        return ParseToEntityConversion.ConvertEvents(objs);
    }

    @Override
    public Event GetById(String id) {
        ParseObject obj = new ParseObject(Event.ENTITY_NAME);
        obj.setObjectId(id);

        try{
            obj.fetch();
        }
        catch(Exception ex){
            Log.Error("Error fetching event by ID: {0}", ex.getMessage());
        }

        return ParseToEntityConversion.ConvertEvent(obj);
    }

    @Override
    public Event Save(Event arg) {
        ParseObject obj = EntityToParseConversion.EventToParseObject(arg);
        final ParseObject objRef = obj;
        final Event eventRef = arg;
        obj.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                eventRef.set_entityId(objRef.getObjectId());
            }
        });
        return arg;
    }

    @Override
    public boolean Delete(Event arg) {
        ParseObject obj = new ParseObject(Event.ENTITY_NAME);
        obj.setObjectId(arg.get_entityId());
        obj.deleteEventually();
        return true;
    }
}
