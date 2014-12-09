package edu.fau.whatsup.Managers;

import android.content.Context;

import com.flurry.android.FlurryAgent;

import java.util.Map;

import edu.fau.whatsup.Entities.Location.LatLon;
import edu.fau.whatsup.Managers.Entities.UserManager;

public class FlurryManager extends BaseManager {

    public static enum Event{
        AddedEvent("Added an Event"),
        EditedEvent("Edited an Event"),
        DeletedEvent("Deleted an Event"),
        SignedUp("User Signed Up"),
        ViewedEvent("Viewed an Event"),
        AttendedEvent("Added self to an Event"),
        RemovedEvent("Removed self from Event")
        ;

        private String _name;
        Event(String name){
            _name = name;
        }

        public String get_name(){
            return _name;
        }
    }

    //region Singleton Stuff
    private static FlurryManager _instance;
    public static void Initialize(Context c){
        if(_instance == null) _instance = new FlurryManager(c);
    }
    public static FlurryManager Instance(){
        return _instance;
    }
    //endregion

    private static final String API_KEY = "T43GSWJ2KK6Q5S23QYW8";

    private FlurryManager(Context c){
        super(c);
        FlurryAgent.onStartSession(c, API_KEY);
        FlurryAgent.setLogEnabled(true);
        ReportLocation();
    }

    public void Destroy(){
        FlurryAgent.onEndSession(_context);
    }

    public void ReportLocation(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                LatLon curLoc = GPSManager.Instance().CurrentLocation();
                FlurryAgent.setLocation((float)curLoc.get_latitude(), (float)curLoc.get_longitude());
            }
        };

        if(GPSManager.Instance().HasLocation()){
            r.run();
        }
        else{
            GPSManager.Instance().WhenLocationSet(r);
        }
    }

    public void UpdateAge(){
        if(UserManager.Instance().GetActiveUser() != null){
            FlurryAgent.setAge(UserManager.Instance().GetActiveUser().get_age());
        }
    }

    public void ReportEvent(Event evt){
        FlurryAgent.logEvent(evt.get_name());
    }

    public void ReportEvent(Event evt, Map<String, String> params){
        FlurryAgent.logEvent(evt.get_name(), params);
    }

    public void StartEvent(Event evt){
        FlurryAgent.logEvent(evt.get_name(), true);
    }

    public void StopEvent(Event evt){
        FlurryAgent.endTimedEvent(evt.get_name());
    }

    public void StartCustomEvent(String event){
        FlurryAgent.logEvent(event, true);
    }

    public void EndCustomEvent(String event){
        FlurryAgent.endTimedEvent(event);
    }

}
