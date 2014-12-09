package edu.fau.whatsup.ServiceContracts.Entities;

import edu.fau.whatsup.Entities.Event;
import edu.fau.whatsup.Entities.Location.LatLon;

import java.util.ArrayList;

public interface IEventService extends IEntityService<Event> {
    ArrayList<Event> RetrieveEventsNear(LatLon loc);
}
