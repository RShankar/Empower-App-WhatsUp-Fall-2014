package edu.fau.whatsup;

import android.app.Application;
import android.os.Bundle;

import edu.fau.whatsup.Managers.BaseManager;
import edu.fau.whatsup.Managers.FlurryManager;

public class WhatsUpApplication extends Application {

    private static final String FLURRY_APP_EVENT = "Ran Application";
    private static WhatsUpApplication _instance;

    public static WhatsUpApplication Instance(){
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseManager.InitializeAll(getApplicationContext(), new Bundle());
        FlurryManager.Instance().StartCustomEvent(FLURRY_APP_EVENT);
    }

    @Override
    public void onTerminate() {
        FlurryManager.Instance().EndCustomEvent(FLURRY_APP_EVENT);
        FlurryManager.Instance().Destroy();
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
