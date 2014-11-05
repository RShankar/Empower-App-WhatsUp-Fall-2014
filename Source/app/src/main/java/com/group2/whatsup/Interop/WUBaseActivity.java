package com.group2.whatsup.Interop;

import android.app.Activity;
import android.os.Bundle;

import com.group2.whatsup.Debug.Log;

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
}
