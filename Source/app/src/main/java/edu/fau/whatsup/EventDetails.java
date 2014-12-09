package edu.fau.whatsup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.fau.whatsup.Debug.Log;
import edu.fau.whatsup.Entities.Event;
import edu.fau.whatsup.Entities.Location.Address;
import edu.fau.whatsup.Helpers.LocationHelper;
import edu.fau.whatsup.Helpers.URIHelper;
import edu.fau.whatsup.Interop.WUBaseActivity;
import edu.fau.whatsup.Managers.Entities.EventManager;
import edu.fau.whatsup.Managers.Entities.UserManager;
import edu.fau.whatsup.Managers.FlurryManager;
import edu.fau.whatsup.Managers.GPSManager;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;


public class EventDetails extends WUBaseActivity {

    public static final String EVENT_CONTEXT_KEY = "CONTEXT";

    private Event _context;

    private ImageButton _btnAttend;
    private Button _btnNavigate;
    private Button _btnWebsite;

    private TextView _lblTitle;
    private TextView _lblAttendees;
    private TextView _lblStreet1;
    private TextView _lblStreet2;
    private TextView _lblCity;
    private TextView _lblState;
    private TextView _lblZip;
    private TextView _lblDate;
    private TextView _lblTime;
    private TextView _lblCategory;
    private TextView _lblDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        retrieveEvent();
        super.onCreate(savedInstanceState, R.layout.activity_event_details, true);
    }

    @Override
    protected void initializeViewControls() {
        super.initializeViewControls();
        _lblTitle = (TextView) findViewById(R.id.eventdetails_eventtitle);
        _btnAttend = (ImageButton) findViewById(R.id.eventdetails_attendbutton);
        _lblAttendees = (TextView) findViewById(R.id.eventdetails_peopleattendinglabel);
        _lblStreet1 = (TextView) findViewById(R.id.eventdetails_addressstreet1);
        _lblStreet2 = (TextView) findViewById(R.id.eventdetails_addressstreet2);
        _lblCity = (TextView) findViewById(R.id.eventdetails_addresscity);
        _lblState = (TextView) findViewById(R.id.eventdetails_addressstate);
        _lblZip = (TextView) findViewById(R.id.eventdetails_addresspostalcode);
        _btnWebsite = (Button) findViewById(R.id.eventdetails_visitwebsite);
        _lblDate = (TextView) findViewById(R.id.eventdetails_startdate);
        _lblTime = (TextView) findViewById(R.id.eventdetails_starttime);
        _lblCategory = (TextView) findViewById(R.id.eventdetails_category);
        _lblDescription = (TextView) findViewById(R.id.eventdetails_description);
        _btnNavigate = (Button) findViewById(R.id.eventdetails_navigate);
    }

    @Override
    protected void setViewTheme(){
        _lblTitle.setText(_context.get_title());
        Address loc = _context.get_address();

        if(loc != null){
            _lblStreet1.setText(loc.StreetLine1);
            _lblStreet2.setText(loc.StreetLine2);
            _lblCity.setText(loc.City);
            _lblState.setText(loc.State);
            _lblZip.setText(loc.PostalCode);
        }

        _lblCategory.setText(_context.get_category_name());
        _lblDescription.setText(_context.get_description());
        UIUtils.ThemeButtons(_btnNavigate, _btnWebsite);

        initAttendButton();
        initAttendeeCount();
        initWebsiteButton();
        initNavigateButton();
        initDateTimeLabels();
    }

    private void initNavigateButton(){
        _btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nav = LocationHelper.GetGoogleMapsIntent(GPSManager.Instance().CurrentLocation(), _context.get_location());
                startActivity(nav);
            }
        });
    }

    private void initDateTimeLabels(){
        SimpleDateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy");
        String date = fmt.format(_context.get_startTime());
        _lblDate.setText(date);

        fmt = new SimpleDateFormat("hh:mm a");
        String time = fmt.format(_context.get_startTime());
        _lblTime.setText(time);

    }

    private void initAttendButton(){
        if(_context.has_attendee(UserManager.Instance().GetActiveUser())){
            _btnAttend.setBackground(getResources().getDrawable(R.drawable.icon_minus));
        }

        _btnAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_context.has_attendee(UserManager.Instance().GetActiveUser())){
                    _context.get_attendees().remove(UserManager.Instance().GetActiveUser());
                    FlurryManager.Instance().ReportEvent(FlurryManager.Event.RemovedEvent);
                    EventManager.Instance().SaveInThread(_context, true);
                    _btnAttend.setBackground(getResources().getDrawable(R.drawable.icon_plus));
                }
                else{
                    _context.get_attendees().add(UserManager.Instance().GetActiveUser());
                    FlurryManager.Instance().ReportEvent(FlurryManager.Event.AttendedEvent);
                    EventManager.Instance().SaveInThread(_context, true);
                    _btnAttend.setBackground(getResources().getDrawable(R.drawable.icon_minus));
                }

                initAttendeeCount();
            }
        });
    }

    private void initAttendeeCount(){
        String str = MessageFormat.format("{0} {1} attending", _context.get_attendeesCount(), _context.get_attendeesCount() == 1 ? "person" : "people");
        _lblAttendees.setText(str);
    }

    private void initWebsiteButton(){
        if(_context.get_website().isEmpty()){
            removeViews(_btnWebsite);
        }
        else{
            _btnWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(URIHelper.FormatIntoWebsite(_context.get_website())));
                    startActivity(i);
                }
            });
        }
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
