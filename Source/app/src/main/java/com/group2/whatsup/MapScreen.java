package com.group2.whatsup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

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
import com.group2.whatsup.Managers.ToastManager;

import java.util.ArrayList;


public class MapScreen extends WUBaseActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    private GoogleMap _googleMap;
    ArrayList<Event> list = new ArrayList<Event>();
    LookupTable<Marker, Event> _lookup = new LookupTable<Marker, Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_map_screen);
    }

    protected void initializeViewControls(){
        _googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        _googleMap.setOnMarkerClickListener(this);
        //_googleMap.setOnMapClickListener(this);
    }

    protected void setViewTheme(){
        mapInit();
    }

    private void mapInit() {
        Runnable whenDone = new Runnable(){

            @Override
            public void run() {
                LatLng current_location = new LatLng(
                        GPSManager.Instance().CurrentLocation().get_latitude(),
                        GPSManager.Instance().CurrentLocation().get_longitude()
                );
                _googleMap.setMyLocationEnabled(true);
                _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 13));
                //_googleMap.addMarker(new MarkerOptions().title("You are here").snippet("Hopefully").position(current_location));
                addEventMarkers();
            }
        };
        if(GPSManager.Instance().HasLocation()){
            whenDone.run();
        }
        else{
            GPSManager.Instance().WhenLocationSet(whenDone);
        }


    }

    private void addEventMarkers() {
        ArrayList<Event> _eventsList = EventManager.Instance().FindEventsNearLastKnownLocation();

        for (Event event: _eventsList)
        {
            Marker m = _googleMap.addMarker(new MarkerOptions()
                            .title(event.get_title())
                            .snippet(event.get_description())
                            .position(event.get_location_LatLng())
                            .draggable(false)
            );
            _lookup.Add(m, event);

            if (event.get_category() == EventCategory.Fitness)
                m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.fitness));
            else if (event.get_category() == EventCategory.Scholastic)
                m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.schoolastic));
            else if (event.get_category() == EventCategory.Sports)
                m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.sports));
            else if (event.get_category() == EventCategory.Volunteering)
                m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.volunteer));
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
    public void onMapClick(LatLng latLng) {
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
