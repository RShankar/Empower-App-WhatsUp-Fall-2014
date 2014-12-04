package com.group2.whatsup.Managers;

import android.content.Context;
import android.os.Bundle;

import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.Entities.UserManager;

public abstract class BaseManager {
    protected Context _context;

    public BaseManager(Context c){
        _context = c;
    }

    public Context get_context(){ return _context; }

    public static void InitializeAll(Context appContext, Bundle state){
        ParseManager.Initialize(appContext);
        SettingsManager.Initialize(state, appContext);
        ToastManager.Initialize(appContext);
        AuthenticationManager.Initialize(appContext);
        UserManager.Initialize(appContext);
        EventManager.Initialize(appContext);
        GPSManager.Initialize(appContext);
    }
}
