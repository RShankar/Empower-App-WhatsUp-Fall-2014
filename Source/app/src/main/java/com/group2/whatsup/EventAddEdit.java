package com.group2.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Location.LatLon;
import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.ToastManager;


public class EventAddEdit extends WUBaseActivity {

    double _latitude,
           _longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_event_add_edit);
        retrieveCoords();
    }

    // Coordinates passed from clicking on the map in a blank spot or current location
    private void retrieveCoords() {
        Bundle b = getIntent().getExtras();
        _latitude = b.getDouble("event_lat");
        _longitude = b.getDouble("event_long");
        ToastManager.Instance().SendMessage(Double.toString(_latitude), true);
    }

    @Override
    protected void initializeViewControls(){

    }

    @Override
    protected void setViewTheme(){

    }
}
