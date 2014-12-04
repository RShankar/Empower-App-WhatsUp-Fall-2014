package com.group2.whatsup.Interop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.group2.whatsup.Debug.Log;
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
