package com.group2.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.Location.LatLon;
import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.ToastManager;


public class EventAddEdit extends WUBaseActivity {

    private Event _context;
    private boolean _editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_event_add_edit);
    }

    @Override
    protected void initializeViewControls(){

    }

    @Override
    protected void setViewTheme(){
        Bundle b = getIntent().getExtras();
        String eventId = b.getString(BUNDLE_EVENT_ID_KEY);
        //Add Mode
        if(eventId == null){
            setupAddMode();
        }

        //Edit Mode
        else {
            Event eventToEdit = EventManager.Instance().GetEvent(eventId);
            if(eventToEdit != null){
                _context = eventToEdit;
                setupEditMode();
            }
            else{
                Log.Error("No event found with ID {0}!", eventId);
                ToastManager.Instance().SendMessage("An error occurred, unable to retrieve event!", true);
            }

        }
    }

    //Edit Mode Logic
    private void setupEditMode(){
        _editMode = true;
    }

    //Add Mode Logic
    private void setupAddMode(){
        _editMode = false;
    }
}
