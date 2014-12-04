package com.group2.whatsup;

import android.app.Application;
import android.os.Bundle;

import com.group2.whatsup.Managers.BaseManager;

public class WhatsUpApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseManager.InitializeAll(getApplicationContext(), new Bundle());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
