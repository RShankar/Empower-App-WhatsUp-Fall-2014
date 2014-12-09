package edu.fau.whatsup.Managers.Entities;

import android.content.Context;

import edu.fau.whatsup.Debug.Log;
import edu.fau.whatsup.Entities.Event;
import edu.fau.whatsup.Entities.Location.LatLon;
import edu.fau.whatsup.Managers.BaseManager;
import edu.fau.whatsup.Managers.GPSManager;
import edu.fau.whatsup.ServiceContracts.Entities.IEventService;
import edu.fau.whatsup.Services.Entities.ParseEventService;

import java.util.ArrayList;

public class EventManager extends BaseManager {

    public interface IEventManagerChanged{
        void added(Event e);
        void removed(Event e);
        void updated(Event e);
    }

    protected enum EMNotificationType{
        Add,
        Remove,
        Modify
    }

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
    private ArrayList<IEventManagerChanged> _notifications;

    private EventManager(Context c){
        super(c);
        _service = new ParseEventService();
        _notifications = new ArrayList<IEventManagerChanged>();
    }

    public ArrayList<Event> FindEventsNear(LatLon location){
        return _service.RetrieveEventsNear(location);
    }

    public ArrayList<Event> FindEventsNearLastKnownLocation(){
        return _service.RetrieveEventsNear(GPSManager.Instance().CurrentLocation());
    }

    public Event GetEvent(String id){
        return _service.GetById(id);
    }

    public boolean Delete(Event event){
        boolean retVal = _service.Delete(event);
        alertListeners(event, EMNotificationType.Remove);
        return retVal;
    }

    public void SaveInThread(final Event event, boolean notify){
        boolean isNew = event.get_entityId() == null;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.Info("Saving event in background thread.");
                _service.Save(event);
            }
        });
        t.setName("Event Save Background Thread");
        t.run();

        if(notify){
            alertListeners(event, isNew ? EMNotificationType.Add : EMNotificationType.Modify);
        }
    }

    public void Save(Event event){
        boolean adding = event.get_entityId() == null;
        _service.Save(event);
        alertListeners(event, adding ? EMNotificationType.Add : EMNotificationType.Modify);
    }

    private void alertListeners(Event e, EMNotificationType type){
        if(type == EMNotificationType.Add){
            for(IEventManagerChanged m : _notifications){
                m.added(e);
            }
        }
        else if (type == EMNotificationType.Remove){
            for(IEventManagerChanged m : _notifications){
                m.removed(e);
            }
        }
        else if (type == EMNotificationType.Modify){
            for(IEventManagerChanged m : _notifications){
                m.updated(e);
            }
        }
    }

    public void ReceiveNotifications(IEventManagerChanged chg){
        _notifications.add(chg);
    }

    public void RemoveNotifications(IEventManagerChanged chg){
        _notifications.remove(chg);
    }

    public void DeleteAll(){
        _service.DeleteAll();
    }

}
