package edu.fau.whatsup.Interop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import edu.fau.whatsup.Entities.Event;
import edu.fau.whatsup.Managers.Entities.UserManager;
import edu.fau.whatsup.R;

public class WUBaseActivity extends FragmentActivity {

    protected final static String BUNDLE_EVENT_ID_KEY = "event_ID";
    protected final static String BUNDLE_EVENT_TITLE_KEY = "event_title";
    protected final static String BUNDLE_EVENT_LATITUDE_KEY = "event_lat";
    protected final static String BUNDLE_EVENT_LONGITUDE_KEY = "event_long";

    protected void onCreate(Bundle savedInstanceState, int viewId){
        super.onCreate(savedInstanceState);
        //Log.Info("onCreate");
        setContentView(viewId);
        initializeViewControls();
        setViewTheme();
    }

    protected void onCreate(Bundle savedInstanceState, int viewId, boolean hideTitle){
        if(hideTitle){
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        onCreate(savedInstanceState, viewId);
    }

    protected void initializeViewControls(){
        //Log.Info("initializeViewControls");
    }

    protected void setViewTheme(){
        //Log.Info("setViewTheme");
    }

    public void changeActivity(LatLng LL, Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        Bundle b = new Bundle();
        b.putDouble(BUNDLE_EVENT_LATITUDE_KEY, LL.latitude);
        b.putDouble(BUNDLE_EVENT_LONGITUDE_KEY, LL.longitude);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void changeActivity(Event e, Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        if(e != null){
            intent.putExtra(BUNDLE_EVENT_TITLE_KEY, e.get_title());
            intent.putExtra(BUNDLE_EVENT_ID_KEY, e.get_entityId());
        }
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

    protected void removeView(View v){
        ((ViewManager)v.getParent()).removeView(v);
    }

    protected void removeViews(View... vs){
        for(View v : vs){
            removeView(v);
        }
    }

    protected void enable(TextView... tvs){
        for(TextView tv : tvs){
            tv.setEnabled(true);
        }
    }

    protected void disable(TextView... tvs){
        for(TextView tv : tvs){
            tv.setEnabled(false);
        }
    }
}
