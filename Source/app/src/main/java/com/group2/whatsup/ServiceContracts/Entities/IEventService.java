package com.group2.whatsup.ServiceContracts.Entities;

import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.Location.LatLon;

import java.util.ArrayList;

public interface IEventService extends IEntityService<Event> {
    ArrayList<Event> RetrieveEventsNear(LatLon loc);
}
