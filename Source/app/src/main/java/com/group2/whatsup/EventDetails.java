package com.group2.whatsup;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Interop.WUBaseActivity;


public class EventDetails extends WUBaseActivity {

    public static final String EVENT_CONTEXT_KEY = "CONTEXT";

    private Event _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object possContext = savedInstanceState.get(EVENT_CONTEXT_KEY);
        if(possContext != null) _context = (Event) possContext;
        super.onCreate(savedInstanceState, R.layout.activity_event_details);
    }


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
