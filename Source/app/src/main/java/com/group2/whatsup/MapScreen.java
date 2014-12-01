package com.group2.whatsup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.group2.whatsup.Interop.WUBaseActivity;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.group2.whatsup.Managers.AuthenticationManager;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.Entities.UserManager;
import com.group2.whatsup.Managers.GPSManager;
import com.group2.whatsup.Managers.ParseManager;
import com.group2.whatsup.Managers.SettingsManager;
import com.group2.whatsup.Managers.ToastManager;

import android.app.Activity;
import android.os.Bundle;


public class MapScreen extends WUBaseActivity {

    private Button _EventButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitializeManagers(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        mapStart();
    }

    private void mapStart() {
        GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        LatLng current_location = new LatLng(
                GPSManager.Instance().CurrentLocation().get_latitude(),
                GPSManager.Instance().CurrentLocation().get_longitude()
        );

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(current_location, 13));

        map.addMarker(new MarkerOptions()
                .title("You are here")
                .snippet("Hopefully")
                .position(current_location));

    }

    private void InitializeManagers(Bundle state) {
        Context appContext = getApplicationContext();
        ParseManager.Initialize(appContext);
        SettingsManager.Initialize(state, appContext);
        ToastManager.Initialize(appContext);
        AuthenticationManager.Initialize(appContext);
        UserManager.Initialize(appContext);
        EventManager.Initialize(appContext);
        GPSManager.Initialize(appContext);
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        /*
        _EventButton = (Button)findViewById(R.id.navigate);
        _EventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch intent
                changeIntent();
            }
        });

    }*/

    public void changeIntent()
    {
        Intent intent = new Intent(this, EventDetails.class);
        startActivity(intent);
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
                Intent intent = new Intent(this, EventList.class);
                startActivity(intent);
                return true;
        }
        /* This is the default thing, I'm just going to leave it */
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
