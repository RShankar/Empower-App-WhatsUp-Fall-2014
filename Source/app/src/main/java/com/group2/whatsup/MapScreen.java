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
import com.group2.whatsup.Entities.Location.LatLon;
import com.group2.whatsup.Interop.WUBaseActivity;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.GPSManager;
import com.group2.whatsup.Managers.ToastManager;

import java.util.ArrayList;


public class MapScreen extends WUBaseActivity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    private GoogleMap _googleMap;
    ArrayList<Event> list = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_map_screen);
        _googleMap.setOnMarkerClickListener(this);
        _googleMap.setOnMapClickListener(this);


    }

    protected void initializeViewControls(){
        _googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    }

    protected void setViewTheme(){
        mapInit();
    }


    private void mapInit() {


        Runnable whenDone = new Runnable(){

            @Override
            public void run() {

                //do fake stuff
                list = FakeStuff.CreateFakeEvents(GPSManager.Instance());
                for (Event event: list) {
                    EventManager.Instance().Save(event);
                }
                // end of fake stuff



                LatLng current_location = new LatLng(
                        GPSManager.Instance().CurrentLocation().get_latitude(),
                        GPSManager.Instance().CurrentLocation().get_longitude()
                );

                _googleMap.setMyLocationEnabled(true);
                _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 13));

                _googleMap.addMarker(new MarkerOptions().title("You are here").snippet("Hopefully").position(current_location));

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

        // this is just temporary until the event manager is working
        // ******######*****#######
        for (Event event: list)  // shouldn't be using list
        {
            _googleMap.addMarker(new MarkerOptions()
                            .title(event.get_title())
                            .snippet(event.get_description())
                            .position(event.get_location_LatLng())
                                    //.icon()
                            .draggable(false)

            );
        }

        /*  This doesn't work yet because I can't get parse to save my list of events.
        for (Event event: _eventsList)
        {
            _googleMap.addMarker(new MarkerOptions()
                            .title(event.get_title())
                            .snippet(event.get_description())
                            .position(event.get_location_LatLng())
                            //.icon()
                            .draggable(false)
            );
        }
        */
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        ToastManager.Instance().SendMessage("Clicked", true);
        ToastManager.Instance().SendMessage(marker.getId(), true);
        Log.Info(marker.getId());
        if (marker.getId().equals("m0")) {
            changeActivity(EventAddEdit.class);
        }
        else
        {
            changeActivity(EventDetails.class);
        }
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        ToastManager.Instance().SendMessage(latLng.toString(), true);
        changeActivity(EventAddEdit.class);
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
