package com.group2.whatsup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.Location.Address;
import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.Entities.EventManager;


public class EventDetails extends WUBaseActivity {

    public static final String EVENT_CONTEXT_KEY = "CONTEXT";

    private Event _context;

    private TextView _eventTitle;
    private TextView _amountAttendees;
    private TextView _timeStart;
    private TextView _timeEnd;
    private TextView _stl1;
    private TextView _stl2;
    private TextView _city;
    private TextView _state;
    private TextView _zip;
    private TextView _description;
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
        _timeStart = (TextView) findViewById(R.id.time);
        _timeEnd = (TextView) findViewById(R.id.timeEnd);
        _stl1 = (TextView) findViewById(R.id.stl1);
        _stl2 = (TextView) findViewById(R.id.stl2);
        _city = (TextView) findViewById(R.id.city);
        _state = (TextView) findViewById(R.id.state);
        _zip = (TextView) findViewById(R.id.zip);
        _description = (TextView) findViewById(R.id.description);
        _website = (TextView) findViewById(R.id.website);
    }

    private void setViewContent() {
        retrieveEvent();  // get event ID from the map marker clicked

        _eventTitle.setText(_context.get_title());
        _amountAttendees.setText(Integer.toString(_context.get_attendeesCount()));
        _timeStart.setText(_context.get_startTime().toString());
        _timeEnd.setText(_context.get_endTime().toString());
        _description.setText(_context.get_description());
        _website.setText(_context.get_website());

        Address a = _context.get_address();
        _stl2.setText(a.StreetLine2);
        _stl1.setText(a.StreetLine1);
        _city.setText(a.City + ",");
        _state.setText(a.State);
        _zip.setText(a.PostalCode);
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
