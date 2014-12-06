package com.group2.whatsup.Interop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.model.LatLng;
import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Event;
import com.group2.whatsup.Managers.Entities.UserManager;
import com.group2.whatsup.R;

public class WUBaseActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState, int viewId){
        super.onCreate(savedInstanceState);
        Log.Info("onCreate");
        setContentView(viewId);
        initializeViewControls();
        setViewTheme();
    }

    protected void initializeViewControls(){
        Log.Info("initializeViewControls");
    }

    protected void setViewTheme(){
        Log.Info("setViewTheme");
    }

    public void changeActivity(LatLng LL, Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        Bundle b = new Bundle();
        b.putDouble("event_lat", LL.latitude);
        b.putDouble("event_long", LL.longitude);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void changeActivity(Event e, Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        intent.putExtra("event_title", e.get_title());
        intent.putExtra("event_ID", e.get_entityId());
        startActivity(intent);
    }

    public void changeActivity(Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //if authenticated, add the menu.
        if(UserManager.Instance().GetActiveUser() != null){
            getMenuInflater().inflate(R.menu.authenticated_menu, menu);
            return true;
        }

        return false;
    }

    protected void runThread(Runnable run){
        Thread thread = new Thread(run);
        thread.start();
    }
}
