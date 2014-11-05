package com.group2.whatsup.Managers;

import android.os.Bundle;

import java.util.HashMap;

public class SettingsManager {

    //region Singleton Stuff
    private static SettingsManager _instance;
    public static SettingsManager Instance(){
        return _instance;
    }
    public static void Initialize(Bundle state){
        _instance = new SettingsManager(state);
    }
    //endregion

    private HashMap<String, Object> _settings;

    private SettingsManager(Bundle state){
        _settings = new HashMap<String, Object>();
        populateInitialSettings(state);
    }

    private void populateInitialSettings(Bundle state){

    }


}
