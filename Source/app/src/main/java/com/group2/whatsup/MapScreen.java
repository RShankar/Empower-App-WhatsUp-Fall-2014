package com.group2.whatsup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.group2.whatsup.Controls.Accordion.AccordionList;
import com.group2.whatsup.Controls.Accordion.AccordionListItem;
import com.group2.whatsup.Controls.Accordion.IAccordionGroupView;
import com.group2.whatsup.Controls.Accordion.IAccordionItemSelected;
import com.group2.whatsup.Controls.Accordion.IAccordionItemView;
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
    private final static int DEFAULT_ZOOMIN_LEVEL = 15;
    private GoogleMap _googleMap;
    ArrayList<Event> _eventsList = null;
    AccordionList<Event> _eventsAccordion = null;
    ExpandableListView _expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_map_screen);
        _googleMap.setOnMarkerClickListener(this);
        _googleMap.setOnMapClickListener(this);


    }

    protected void initializeViewControls(){
        _googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        _expListView = (ExpandableListView) findViewById(R.id.map_listView);
    }

    protected void setViewTheme(){
        mapInit();
    }

    private void accordionInit(){
        _eventsAccordion = new AccordionList<Event>();
        _eventsAccordion.CreateFromListAndSectionSpecification(_eventsList, "get_category_name");

        //region Action On Click
        _eventsAccordion.SetActionOnClick(new IAccordionItemSelected<Event>() {
            @Override
            public void ItemSelected(Event selectedItem) {
                LatLng coordinate = selectedItem.get_location().get_LatLng();
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, DEFAULT_ZOOMIN_LEVEL);
                _googleMap.animateCamera(yourLocation);
                ToastManager.Instance().SendMessage(selectedItem.get_title() + " clicked, zooming to location.", false);
            }
        });
        //endregion

        //region Group View
        _eventsAccordion.SetGroupViewToAppear(new IAccordionGroupView<Event>() {
            @Override
            public View viewToAppear(AccordionListItem<Event> item, View convertView) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.event_group, null);
                }
                TextView txt = (TextView) convertView.findViewById(R.id.group);
                txt.setTypeface(null, Typeface.BOLD);
                txt.setText(item.GetLabel());
                return convertView;
            }
        });
        //endregion

        //region Item View
        _eventsAccordion.SetViewToAppear(new IAccordionItemView<Event>() {
            @Override
            public View viewToDisplay(Event item, View convertView) {

                LayoutInflater inflater = getLayoutInflater();

                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.event_child, null);
                }

                TextView txt = (TextView) convertView.findViewById(R.id.child);
                txt.setText(item.get_title());

                return convertView;
            }
        });
        //endregion

        _eventsAccordion.InitializeExpandableListView(_expListView);
    }


    private void mapInit() {


        Runnable whenDone = new Runnable(){

            @Override
            public void run() {
                addEventMarkers();
                accordionInit();

                LatLng current_location = new LatLng(
                        GPSManager.Instance().CurrentLocation().get_latitude(),
                        GPSManager.Instance().CurrentLocation().get_longitude()
                );

                _googleMap.setMyLocationEnabled(true);
                _googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 13));

                _googleMap.addMarker(new MarkerOptions().title("You are here").snippet("Hopefully").position(current_location));
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
        _eventsList = EventManager.Instance().FindEventsNearLastKnownLocation();

        Log.Info("Found {0} Events!", _eventsList.size());
        for (Event event: _eventsList)
        {
            Log.Info("Adding event with title: {0}", event.get_title());
            _googleMap.addMarker(new MarkerOptions()
                            .title(event.get_title())
                            .snippet(event.get_description())
                            .position(event.get_location_LatLng())
                                    //.icon()
                            .draggable(false)

            );
        }
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
