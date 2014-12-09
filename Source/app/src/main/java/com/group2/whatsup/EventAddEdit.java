package com.group2.whatsup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Entities.EventCategory;
import com.group2.whatsup.Entities.Location.Address;
import com.group2.whatsup.Entities.Location.LatLon;
import com.group2.whatsup.Helpers.LocationHelper;
import com.group2.whatsup.Helpers.Validate;
import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.Entities.UserManager;
import com.group2.whatsup.Managers.GPSManager;
import com.group2.whatsup.Managers.ToastManager;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EventAddEdit extends WUBaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Event _context;
    private boolean _editMode;
    private Date _startDate;
    private final Calendar _cal = Calendar.getInstance();
    private Address _oldAddress;

    //region Controls
    private EditText _txtTitle;
    private EditText _txtAddStreet1;
    private EditText _txtAddStreet2;
    private EditText _txtAddCity;
    private EditText _txtAddState;
    private EditText _txtAddPostalCode;
    private Button _btnSave;
    private Button _btnCancel;
    private Button _btnDelete;
    private CheckBox _chkUseCurrentLocation;
    private GridLayout _addressContainer;
    private EditText _txtWebsite;
    private EditText _txtDescription;
    private EditText _txtPhone;
    private Spinner _selEventCategory;
    private TextView _lblDate;
    private Button _btnChangeDate;
    private Button _btnChangeTime;
    private DatePickerDialog _datePicker;
    private TimePickerDialog _timePicker;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_event_add_edit, true);
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
        _txtWebsite = (EditText) findViewById(R.id.eventaddedit_website);
        _txtDescription = (EditText) findViewById(R.id.eventaddedit_description);
        _txtPhone = (EditText) findViewById(R.id.eventaddedit_phonenumber);
        _selEventCategory = (Spinner) findViewById(R.id.eventaddedit_categoryselect);
        _startDate = _cal.getTime();
        _datePicker = DatePickerDialog.newInstance(this, _cal.get(Calendar.YEAR), _cal.get(Calendar.MONTH), _cal.get(Calendar.DAY_OF_MONTH));
        _timePicker = TimePickerDialog.newInstance(this, _cal.get(Calendar.HOUR_OF_DAY), _cal.get(Calendar.MINUTE), false, false);
        _lblDate = (TextView) findViewById(R.id.eventaddedit_starttime);
        _btnChangeDate = (Button) findViewById(R.id.eventaddedit_changedate);
        _btnChangeTime = (Button) findViewById(R.id.eventaddedit_changetime);
        _btnDelete = (Button) findViewById(R.id.eventaddedit_delete);
    }

    @Override
    protected void setViewTheme(){

        UIUtils.ThemeTextboxes(_txtTitle, _txtAddStreet1, _txtAddStreet2, _txtAddCity, _txtAddState, _txtAddPostalCode, _txtDescription, _txtWebsite, _txtPhone);
        UIUtils.ThemeButtons(_btnSave, _btnCancel, _btnChangeDate, _btnChangeTime, _btnDelete);
        _chkUseCurrentLocation.setText(R.string.eventaddedit_checkbox_location_current);

        //region Spinner Setup
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EventCategory.getCategoryNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _selEventCategory.setAdapter(adapter);
        //endregion

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

        _btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _datePicker.show(getSupportFragmentManager(), Log.TAG);
            }
        });

        _btnChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _timePicker.show(getSupportFragmentManager(), Log.TAG);
            }
        });

        _btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dLog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                EventManager.Instance().Delete(_context);
                                ToastManager.Instance().SendMessage("Event Deleted!", true);
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(EventAddEdit.this);
                builder.setMessage("Are you sure you want to delete this event?");
                builder.setPositiveButton("Yes", dLog);
                builder.setNegativeButton("No", dLog);
                builder.show();
            }
        });

        _chkUseCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_chkUseCurrentLocation.isChecked()){
                    if(_context.get_address() != null) _oldAddress = _context.get_address();

                    Runnable whenDone = new Runnable() {
                        @Override
                        public void run() {
                            _context.set_location(GPSManager.Instance().CurrentLocation());
                            Address ret = LocationHelper.GetAddressFromLatLon(_context.get_location());
                            setAddressFields(ret);
                        }
                    };

                    if(GPSManager.Instance().HasLocation()){
                        whenDone.run();
                    }
                    else{
                        GPSManager.Instance().WhenLocationSet(whenDone);
                    }

                    disable(_txtAddStreet1, _txtAddStreet2, _txtAddCity, _txtAddState, _txtAddPostalCode);
                }
                else{
                    if(_oldAddress != null) setAddressFields(_oldAddress);
                    _oldAddress = null;
                    enable(_txtAddStreet1, _txtAddStreet2, _txtAddCity, _txtAddState, _txtAddPostalCode);
                }
            }
        });
        //endregion

        updateTimeText();

        setupViewMode();
    }

    private void setAddressFields(Address add){
        Log.Debug("Setting address fields to {0}", add);
        _txtAddStreet1.setText(add.StreetLine1);
        _txtAddStreet2.setText(add.StreetLine2);
        _txtAddCity.setText(add.City);
        _txtAddState.setText(add.State);
        _txtAddPostalCode.setText(add.PostalCode);
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

    //Retrieval of the LatLon object from the bundle.
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
        _startDate = _context.get_startTime();
        setTitle("Edit Your Event");
        _txtTitle.setText(_context.get_title());
        Address existing = _context.get_address();
        _txtAddStreet1.setText(existing.StreetLine1);
        _txtAddStreet2.setText(existing.StreetLine2);
        _txtAddCity.setText(existing.City);
        _txtAddState.setText(existing.State);
        _txtAddPostalCode.setText(existing.PostalCode);
        _txtDescription.setText(_context.get_description());
        _txtWebsite.setText(_context.get_website());
        _txtPhone.setText(_context.get_phone());
        EventCategory selected = _context.get_category();
        List<String> allCats = EventCategory.getCategoryNames();
        for(int i = 0; i < allCats.size(); i++){
            if(allCats.get(i).equals(selected.getName())){
                _selEventCategory.setSelection(i);
                break;
            }
        }

        updateTimeText();
    }

    //Removes the delete button when adding.
    private void removeDeleteButton(){
        removeViews(_btnDelete);
        /*
        _txtTitle.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        */
    }

    //Add Mode Logic
    private void setupAddMode(){
        _context = new Event();
        _editMode = false;
        removeDeleteButton();
        setTitle("Add a new Event");

        final LatLon locFromLongClick = getLatLonFromBundle();

        //Location from the map long click event.
        if(locFromLongClick != null) {
            _chkUseCurrentLocation.setText(R.string.eventaddedit_checkbox_location_selected);
            _chkUseCurrentLocation.setChecked(true);
            _chkUseCurrentLocation.setEnabled(false);
            removeViews(_addressContainer);
            _context.set_location(locFromLongClick);

            //region Update Address from background.
            runThread(new Runnable() {
                @Override
                public void run() {
                    Log.Info("Finding address in separate thread for {0}", locFromLongClick);
                    final Address loc = LocationHelper.GetAddressFromLatLon(locFromLongClick);
                    if(loc != null){
                        Log.Info("Found address: {0}", loc);
                        setAddressFields(loc);
                    }
                    else Log.Info("No address found!");
                }
            });
            //endregion
        }
    }

    //Save Logic
    private void save(){
        Event targetEvent = eventFromFields();
        if(validate(targetEvent)){
            if(_context.get_location() == null && _context.get_address() != null){
                _context.set_location(LocationHelper.GetLatLonFromAddress(_context.get_address()));
            }

            EventManager.Instance().Save(targetEvent);
            String retMsg = _editMode ? "Event Updated!" : "Event Saved!";
            ToastManager.Instance().SendMessage(retMsg, true);
            finish();
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
        retVal.set_description(UIUtils.getText(_txtDescription));
        retVal.set_website(UIUtils.getText(_txtWebsite));
        retVal.set_phone(UIUtils.getText(_txtPhone));
        retVal.set_category(EventCategory.fromName(_selEventCategory.getSelectedItem().toString()));
        retVal.set_startTime(_startDate);
        retVal.set_owner(UserManager.Instance().GetActiveUser());

        return retVal;
    }

    //Validates all fields.
    private boolean validate(Event e){
        boolean retVal = true;

        ArrayList<Validate.Result> res = new ArrayList<Validate.Result>();
        if(!_chkUseCurrentLocation.isChecked()){
            res.add(Validate.Address(e.get_address()));
        }

        res.add(Validate.EventTitle(e.get_title()));
        res.add(Validate.Website(e.get_website()));
        res.add(Validate.EventDescription(e.get_description()));
        res.add(Validate.Phone(e.get_phone()));


        StringBuilder sb = new StringBuilder();
        for(Validate.Result r : res){
            if(!r.valid){
                sb.append(r.message + "\n");
                retVal = false;
            }
        }

        if(!retVal){
            valMsg(sb.toString().substring(0, sb.toString().length() - 1));
        }

        return retVal;
    }

    private void valMsg(String message){
        ToastManager.Instance().SendMessage(message, true);
    }

    private void updateTimeText(){
        SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String date = fmt.format(_startDate);
        _lblDate.setText(date);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int i, int i2, int i3) {
        Log.Debug("i={0}, i2={1}, i3={2}", i, i2, i3);
        _cal.setTime(_startDate);
        _cal.set(i, i2, i3);
        _startDate = _cal.getTime();
        updateTimeText();
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i2) {
        Log.Debug("i={0}, i2={1}", i, i2);
        _cal.setTime(_startDate);
        _cal.set(_cal.get(Calendar.YEAR), _cal.get(Calendar.MONTH), _cal.get(Calendar.DAY_OF_MONTH), i, i2, 0);
        _startDate = _cal.getTime();
        updateTimeText();
    }
}
