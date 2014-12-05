package com.group2.whatsup.UnitTests;
import android.test.InstrumentationTestCase;

import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Authentication.User;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.EventCategory;
import com.group2.whatsup.Entities.Location.Address;
import com.group2.whatsup.Entities.Location.LatLon;
import com.group2.whatsup.Helpers.LocationHelper;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.Entities.UserManager;
import com.group2.whatsup.Managers.GPSManager;
import com.group2.whatsup.Managers.ParseManager;

import java.util.ArrayList;
import java.util.Date;

public class EventTests extends InstrumentationTestCase{

    private Event getEvent(User owner){
        Event retVal = new Event();

        retVal.set_category(EventCategory.Sports);
        retVal.set_startTime(new Date());
        retVal.set_endTime(new Date());
        retVal.set_website("http://www.google.com");
        retVal.set_title("Test Event");
        retVal.set_owner(owner);

        Address loc = new Address();
        loc.StreetLine1 = "8214 Shadow Wood blvd";
        loc.City = "Coral Springs";
        loc.State = "FL";
        loc.PostalCode = "33071";
        retVal.set_address(loc);

        LatLon directLoc = LocationHelper.GetLatLonFromAddress(loc);
        retVal.set_location(directLoc);

        retVal.set_description("This is a test event with a slightly longer description than title so that we can see how this item will save, wrap, etc where necessary to fulfill our testing needs.");

        return retVal;
    }

    private User getUser(){
        return UserManager.Instance().GetUserByUsername("test@test.com");
    }

    public void testCreateEvent(){
        User u = getUser();
        Event newEvent = getEvent(u);
        newEvent.get_attendees().add(u);
        EventManager.Instance().Save(newEvent);
        String newEventId = newEvent.get_entityId();
        assertTrue(newEventId != null);

        Event retrievedEvent = EventManager.Instance().GetEvent(newEventId);


        while(!GPSManager.Instance().HasLocation()){
            Log.Info("Waiting for GPS Coordinates...");
        }
        ArrayList<Event> eventsNear = EventManager.Instance().FindEventsNearLastKnownLocation();

        EventManager.Instance().Delete(retrievedEvent);
        assertTrue(retrievedEvent.get_title().equals(newEvent.get_title()));
        assertTrue(retrievedEvent.get_category().equals(newEvent.get_category()));
        assertTrue(retrievedEvent.get_attendeesCount() == newEvent.get_attendeesCount());
    }
}
