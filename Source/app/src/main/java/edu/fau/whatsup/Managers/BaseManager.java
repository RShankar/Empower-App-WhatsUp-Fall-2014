package edu.fau.whatsup.Managers;

import android.content.Context;
import android.os.Bundle;

import edu.fau.whatsup.Managers.Entities.EventManager;
import edu.fau.whatsup.Managers.Entities.UserManager;

public abstract class BaseManager {
    protected Context _context;

    public BaseManager(Context c){
        _context = c;
    }

    public Context get_context(){ return _context; }

    public static void InitializeAll(Context appContext, Bundle state){
        ParseManager.Initialize(appContext);
        ToastManager.Initialize(appContext);
        SettingsManager.Initialize(state, appContext);
        AuthenticationManager.Initialize(appContext);
        UserManager.Initialize(appContext);
        EventManager.Initialize(appContext);
        FlurryManager.Initialize(appContext);
        GPSManager.Initialize(appContext);
    }
}
