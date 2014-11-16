package com.group2.whatsup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.GPSManager;


public class EventList extends WUBaseActivity {

    private Button _EventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(GPSManager.Instance().HasLocation()){
            EventManager.Instance().FindEventsNear(GPSManager.Instance().CurrentLocation());
        }
        setContentView(R.layout.activity_event_list);
        _EventButton = (Button)findViewById(R.id.button);
        _EventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch intent
                changeIntent();
            }
        });
    }

    public void changeIntent()
    {
        Intent intent = new Intent(this, EventDetails.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();        switch (item.getItemId()) {
            case R.id.map:
                // open activity
                Intent intent = new Intent(this, MapScreen.class);
                startActivity(intent);
                return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
