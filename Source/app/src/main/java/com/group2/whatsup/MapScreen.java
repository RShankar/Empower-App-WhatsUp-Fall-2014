package com.group2.whatsup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.group2.whatsup.Interop.WUBaseActivity;


public class MapScreen extends WUBaseActivity {

    private Button _EventButton;

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
        */
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
