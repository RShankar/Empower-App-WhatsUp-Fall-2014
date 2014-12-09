package edu.fau.whatsup.Services.Entities;

import edu.fau.whatsup.Debug.Log;
import edu.fau.whatsup.Entities.Event;
import edu.fau.whatsup.Entities.Location.LatLon;
import edu.fau.whatsup.Managers.SettingsManager;
import edu.fau.whatsup.Managers.ToastManager;
import edu.fau.whatsup.ServiceContracts.Entities.IEventService;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ParseEventService extends BaseParseService implements IEventService {

    private static Event eventRef = new Event();

    private void includeOwnerAndAttendees(ParseQuery<ParseObject> query){
        query.include("owner");
        query.include("attendees");
    }

    @Override
    public ArrayList<Event> RetrieveEventsNear(LatLon loc) {
        ParseQuery<ParseObject> query = queryFor(Event.ENTITY_NAME);
        ParseGeoPoint targetPoint = new ParseGeoPoint(loc.get_latitude(), loc.get_longitude());
        query.whereWithinMiles("location", targetPoint, SettingsManager.Instance().DistancePreference());
        includeOwnerAndAttendees(query);


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
        ParseObject obj = null;
        ParseQuery<ParseObject> query = queryFor(Event.ENTITY_NAME);
        includeOwnerAndAttendees(query);
        try {
            obj = query.get(id);
        } catch (ParseException e) {
            Log.Error("Error fetching event by ID: {0}", e.getMessage());
            return null;
        }

        return ParseToEntityConversion.ConvertEvent(obj);
    }

    @Override
    public Event Save(Event arg) {
        ParseObject obj = EntityToParseConversion.EventToParseObject(arg);
        final ParseObject objRef = obj;
        final Event eventRef = arg;
        try {
            obj.save();
            arg.set_entityId(obj.getObjectId());
        } catch (ParseException e) {
            Log.Error("Failed saving event. from parse is {0}", e.getMessage());
        }
        return arg;
    }

    @Override
    public boolean Delete(Event arg) {
        ParseObject obj = new ParseObject(Event.ENTITY_NAME);
        obj.setObjectId(arg.get_entityId());
        try {
            obj.delete();
        } catch (ParseException e) {
            Log.Error("Failed to delete event: {0}", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void DeleteAll(){
        ParseQuery<ParseObject> query = queryFor(Event.ENTITY_NAME);

        try{
            List<ParseObject> result = query.find();
            ParseObject.deleteAll(result);
        }
        catch(Exception ex){
            ToastManager.Instance().SendMessage("Failed to delete all events from Parse!", true);
        }
    }
}
