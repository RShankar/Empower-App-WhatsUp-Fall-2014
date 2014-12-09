package edu.fau.whatsup.Debug;

import edu.fau.whatsup.Entities.Authentication.User;
import edu.fau.whatsup.Entities.Event;
import edu.fau.whatsup.Entities.EventCategory;
import edu.fau.whatsup.Entities.Location.Address;
import edu.fau.whatsup.Entities.Location.LatLon;
import edu.fau.whatsup.Managers.GPSManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Keith on 12/4/2014.
 * This might not be needed.
 */
public class FakeStuff {
    public static ArrayList<Event> CreateFakeEvents(GPSManager gps)
    {
        Event event1 = new Event();
        event1.set_title("event 1");
        ArrayList<String> s = new ArrayList<String>();
        event1.set_imageNames(s);
        Address A = new Address();
        A.StreetLine1 = "sl1";
        A.StreetLine2 = "sl2";
        A.City = "citys";
        A.State = "st";
        A.PostalCode = "33334";
        event1.set_address(A);
        ArrayList<User> U = new ArrayList<User>();
        event1.set_attendees(U);
        event1.set_category(EventCategory.Fitness);
        event1.set_description("a whole latta fun");
        User u = new User();
        event1.set_owner(u);

        Date D = new Date();
        D.setTime(System.currentTimeMillis());
        event1.set_startTime(D);
        LatLon LL = new LatLon(gps.CurrentLocation().get_latitude() - 0.005,gps.CurrentLocation().get_longitude() - 0.005);
        event1.set_location(LL);
        event1.set_website("www.awesome.com");
        event1.set_entityId("01");

        /*
        Event event2 = new Event();
        event2.set_title("event 2");
        //event1.set_imageNames();
        //event1.set_address();
        //event1.set_attendees();
        event2.set_category(EventCategory.Fitness);
        event2.set_description("a whole latta fun");
        //event1.set_owner();
        //event1.set_startTime();
        //event1.set_endTime();
        LatLon LL2 = new LatLon(gps.CurrentLocation().get_latitude() + 0.5,gps.CurrentLocation().get_longitude() + 0.5);
        event2.set_location(LL2);
        event2.set_website("www.awesome.com");
        event2.set_entityId("02");
        */

        ArrayList<Event> list = new ArrayList<Event>();
        list.add(event1);
        //list.add(event2);

        return list;
    }
}
