package com.group2.whatsup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.group2.whatsup.Debug.FakeStuff;
import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.Location.Address;
import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.GPSManager;
import com.group2.whatsup.Managers.SettingsManager;
import com.group2.whatsup.Managers.ToastManager;

import java.util.ArrayList;


public class EventDetails extends WUBaseActivity {

    public static final String EVENT_CONTEXT_KEY = "CONTEXT";

    private Event _context;

    private TextView _eventTitle;
    private TextView _amountAttendees;
    private TextView _time;
    private TextView _address;
    private TextView _telephone;
    private TextView _website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            Object possContext = savedInstanceState.get(EVENT_CONTEXT_KEY);
            if(possContext != null) _context = (Event) possContext;
        }
        super.onCreate(savedInstanceState, R.layout.activity_event_details);
        setViewContent();
    }

    @Override
    protected void initializeViewControls() {
        super.initializeViewControls();
        _eventTitle = (TextView) findViewById(R.id.eventTitle);
        _amountAttendees = (TextView) findViewById(R.id.amountAttendees);
        _time = (TextView) findViewById(R.id.time);
        _address = (TextView) findViewById(R.id.address);
        _telephone = (TextView) findViewById(R.id.telephone);
        _website = (TextView) findViewById(R.id.website);
    }

    private void setViewContent() {
        retrieveEvent();  // get event ID from the map marker clicked

        _eventTitle.setText(_context.get_title());
        _amountAttendees.setText(Integer.toString(_context.get_attendeesCount()));
        _time.setText(_context.get_startTime().toString());
        Address a = _context.get_address();
        _address.setText(a.StreetLine1);
        //_telephone.setText(_context.get_);
        _website.setText(_context.get_website());
    }

    // get event ID from the marker that was clicked.
    private void retrieveEvent() {
        Intent i = getIntent();
        Log.Info(i.getStringExtra("event_title"));
        Log.Info(i.getStringExtra("event_ID"));
        _context = EventManager.Instance().GetEvent(i.getStringExtra("event_ID"));
    }

    // pull out later?
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
