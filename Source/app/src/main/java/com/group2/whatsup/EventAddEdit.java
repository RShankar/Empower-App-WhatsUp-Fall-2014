package com.group2.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;

import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.Location.Address;
import com.group2.whatsup.Entities.Location.LatLon;
import com.group2.whatsup.Helpers.Validate;
import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.ToastManager;


public class EventAddEdit extends WUBaseActivity {

    private Event _context;
    private boolean _editMode;
    private EditText _txtTitle;
    private EditText _txtAddStreet1;
    private EditText _txtAddStreet2;
    private EditText _txtAddCity;
    private EditText _txtAddState;
    private EditText _txtAddPostalCode;
    private Button _btnSave;
    private Button _btnCancel;
    private CheckBox _chkUseCurrentLocation;
    private GridLayout _addressContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_event_add_edit);
    }

    @Override
    protected void initializeViewControls(){
        _txtTitle = (EditText) findViewById(R.id.eventaddedit_title);
        _txtAddStreet1 = (EditText) findViewById(R.id.eventaddedit_address_street1);
        _txtAddStreet2 = (EditText) findViewById(R.id.eventaddedit_address_street2);
        _txtAddCity = (EditText) findViewById(R.id.eventaddedit_address_city);
        _txtAddState = (EditText) findViewById(R.id.eventaddedit_address_state);
        _txtAddPostalCode = (EditText) findViewById(R.id.eventaddedit_address_postalcode);
        _btnSave = (Button) findViewById(R.id.eventaddedit_btnSave);
        _btnCancel = (Button) findViewById(R.id.eventaddedit_btnCancel);
        _chkUseCurrentLocation = (CheckBox) findViewById(R.id.eventaddedit_usecurrentlocation);
        _addressContainer = (GridLayout) findViewById(R.id.eventaddedit_addresscontainer);
    }

    @Override
    protected void setViewTheme(){

        UIUtils.ThemeTextboxes(_txtTitle, _txtAddStreet1, _txtAddStreet2, _txtAddCity, _txtAddState, _txtAddPostalCode);
        UIUtils.ThemeButtons(_btnSave, _btnCancel);


        //region Click Listeners.
        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        _btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        //endregion

        setupViewMode();


    }

    //Sets up the view mode, either add or edit.
    private void setupViewMode(){
        Bundle b = getIntent().getExtras();

        //Add Mode.
        if(b == null){
            setupAddMode();
        }
        //Potential for edit mode.
        else{
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
    }

    private LatLon getLatLonFromBundle(){
        LatLon retVal = null;

        Bundle b = getIntent().getExtras();
        if(b != null){
            double latitude = b.getDouble(BUNDLE_EVENT_LATITUDE_KEY);
            double longitude = b.getDouble(BUNDLE_EVENT_LONGITUDE_KEY);

            if(latitude != 0 && longitude != 0){
                retVal = new LatLon(latitude, longitude);
            }
        }

        return retVal;
    }

    //Edit Mode Logic
    private void setupEditMode(){
        _editMode = true;
        setTitle("Edit Your Event");
        _txtTitle.setText(_context.get_title());
        Address existing = _context.get_address();
        _txtAddStreet1.setText(existing.StreetLine1);
        _txtAddStreet2.setText(existing.StreetLine2);
        _txtAddCity.setText(existing.City);
        _txtAddState.setText(existing.State);
        _txtAddPostalCode.setText(existing.PostalCode);
    }

    //Add Mode Logic
    private void setupAddMode(){
        _editMode = false;
        setTitle("Add a new Event");

        LatLon locFromLongClick = getLatLonFromBundle();

        //In the event this was a direct add, not from a long click on the map.
        if(locFromLongClick == null){
            _chkUseCurrentLocation.setText(R.string.eventaddedit_checkbox_location_current);
        }

        //Location from the map long click event.
        else{
            _chkUseCurrentLocation.setText(R.string.eventaddedit_checkbox_location_selected);
            _chkUseCurrentLocation.setChecked(true);
            _chkUseCurrentLocation.setEnabled(false);
            removeViews(_addressContainer);
        }
    }

    //Save Logic
    private void save(){
        Event targetEvent = eventFromFields();
        if(validate(targetEvent)){
            EventManager.Instance().Save(targetEvent);
        }
    }

    //Cancel Logic
    private void cancel(){
        finish();
    }

    //Retrieve an event where necessary.
    private Event eventFromFields(){
        Event retVal = _context;

        retVal.set_title(UIUtils.getText(_txtTitle));
        Address newAddress = new Address();
        newAddress.StreetLine1 = UIUtils.getText(_txtAddStreet1);
        newAddress.StreetLine2 = UIUtils.getText(_txtAddStreet2);
        newAddress.City = UIUtils.getText(_txtAddCity);
        newAddress.State = UIUtils.getText(_txtAddState);
        newAddress.PostalCode = UIUtils.getText(_txtAddPostalCode);
        retVal.set_address(newAddress);

        return retVal;
    }

    //Validates all fields.
    private boolean validate(Event e){
        return Validate.Event(e);
    }
}
