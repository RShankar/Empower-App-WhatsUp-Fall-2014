package com.group2.whatsup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.group2.whatsup.Debug.FakeStuff;
import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.EventCategory;
import com.group2.whatsup.Entities.Location.LatLon;
import com.group2.whatsup.Helpers.IDHelper;
import com.group2.whatsup.Helpers.LookupTable;
import com.group2.whatsup.Interop.WUBaseActivity;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.Entities.UserManager;
import com.group2.whatsup.Managers.GPSManager;
import com.group2.whatsup.Managers.SettingsManager;
import com.group2.whatsup.Managers.ToastManager;

import java.util.ArrayList;


public class MapScreen extends WUBaseActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener {
    private static final int DEFAULT_ZOOM_LEVEL = 15;
    private RelativeLayout _background;
    private GoogleMap _googleMap;
    ArrayList<Event> list = new ArrayList<Event>();
    LookupTable<Marker, Event> _lookup = new LookupTable<Marker, Event>();

    private EventManager.IEventManagerChanged _emUpdates = new EventManager.IEventManagerChanged() {
        @Override
        public void added(Event e) {
            Log.Info("Received Event Add Notification: {0}", e.get_title());
            addEventMarker(e);
            zoomToEvent(e);
        }

        @Override
        public void removed(Event e) {
            Log.Info("Received Event Delete Notification: {0}", e.get_title());
            removeEventMarker(e);
        }

        @Override
        public void updated(Event e) {
            Log.Info("Received Event Update Notification: {0}", e.get_title());
            removeEventMarker(e);
            addEventMarker(e);
            zoomToEvent(e);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_map_screen);
        EventManager.Instance().ReceiveNotifications(_emUpdates);
    }

    protected void onStop(){
        super.onStop();
        EventManager.Instance().RemoveNotifications(_emUpdates);
    }

    protected void initializeViewControls(){
        _background = (RelativeLayout) findViewById(R.id.map_mapBackground);
        _googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        _googleMap.setOnMarkerClickListener(this);
        _googleMap.setOnMapLongClickListener(this);
    }

    protected void setViewTheme(){
        _background.setBackgroundColor(SettingsManager.Instance().SecondaryColor());
        mapInit();
    }

    private void mapInit() {
        Runnable whenDone = new Runnable(){

            @Override
            public void run() {
                boolean gpsLoc = GPSManager.Instance().HasLocation();

                double lat = gpsLoc ? GPSManager.Instance().CurrentLocation().get_latitude() : UserManager.Instance().GetActiveUser().get_lastKnownLocation().get_latitude();
                double lon = gpsLoc ? GPSManager.Instance().CurrentLocation().get_longitude() : UserManager.Instance().GetActiveUser().get_lastKnownLocation().get_longitude();

                LatLng current_location = new LatLng(
                        lat,
                        lon
                );

                _googleMap.setMyLocationEnabled(true);
                _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, DEFAULT_ZOOM_LEVEL));
                addEventMarkers(new LatLon(lat, lon));
            }
        };
        if(GPSManager.Instance().HasLocation() || UserManager.Instance().GetActiveUser().has_lastKnownLocation()){
            whenDone.run();
        }
        else{
            GPSManager.Instance().WhenLocationSet(whenDone);
        }
    }

    private void addEventMarkers(LatLon loc) {
        ArrayList<Event> _eventsList = EventManager.Instance().FindEventsNear(loc);

        for (Event event: _eventsList)
        {
            addEventMarker(event);
        }
    }

    private void addEventMarker(Event event){
        Marker m = _googleMap.addMarker(new MarkerOptions()
                        .title(event.get_title())
                        .snippet(event.get_description())
                        .position(event.get_location_LatLng())
                        .draggable(false)
        );
        _lookup.Add(m, event);

        if (event.get_category() == EventCategory.Fitness)
            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.fitness_coin));
        else if (event.get_category() == EventCategory.Scholastic)
            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.schoolastic_coin));
        else if (event.get_category() == EventCategory.Sports)
            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.sports_coin));
        else if (event.get_category() == EventCategory.Volunteering)
            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.volunteer_coin));
    }

    private void removeEventMarker(Event e){
        Marker m = _lookup.GetKey(e);

        if(m != null){
            _lookup.RemoveKey(m);
            m.remove();
        }
    }

    private void zoomToEvent(Event e){
        Marker m = _lookup.GetKey(e);
        if(m != null){
            _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(m.getPosition(), DEFAULT_ZOOM_LEVEL));
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Log.Info("Marker ID Selected: {0}", marker.getId());
        Event targetedEvent = _lookup.GetVal(marker);

        if(targetedEvent != null){
            if(targetedEvent.get_owner().get_entityId().equals(UserManager.Instance().GetActiveUser().get_entityId())){
                Log.Info("Owner is the current user. Switching to add/edit.");
                changeActivity(targetedEvent, EventAddEdit.class);
            }
            else{
                Log.Info("Owner is not the current user. Switching to details.");
                changeActivity(targetedEvent, EventDetails.class);
            }
        }

        return targetedEvent == null;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        changeActivity(latLng, EventAddEdit.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.list:
                // open activity
                changeActivity(EventList.class);
                return true;
        }
        /* This is the default thing, I'm just going to leave it */
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
