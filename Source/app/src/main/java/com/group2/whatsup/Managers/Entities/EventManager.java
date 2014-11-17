package com.group2.whatsup.Managers.Entities;

import android.content.Context;

import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.Location.LatLon;
import com.group2.whatsup.Managers.BaseManager;
import com.group2.whatsup.ServiceContracts.Entities.IEventService;
import com.group2.whatsup.Services.Entities.ParseEventService;

import java.util.ArrayList;

public class EventManager extends BaseManager {
    //region Singleton Crap
    private static EventManager _instance;
    public static void Initialize(Context c){
        if(_instance == null) _instance = new EventManager(c);
    }
    public static EventManager Instance(){
        return _instance;
    }
    //endregion

    private IEventService _service;

    private EventManager(Context c){
        super(c);
        _service = new ParseEventService();
    }

    public ArrayList<Event> FindEventsNear(LatLon location){
        return _service.RetrieveEventsNear(location);
    }

    public Event GetEvent(String id){
        return _service.GetById(id);
    }

    public void Save(Event event){
        _service.Save(event);
    }

}
