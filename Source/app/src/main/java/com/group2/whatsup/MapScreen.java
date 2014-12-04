package com.group2.whatsup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.group2.whatsup.Interop.WUBaseActivity;


import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.group2.whatsup.Managers.GPSManager;


public class MapScreen extends WUBaseActivity {
    private GoogleMap _googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_map_screen);
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
