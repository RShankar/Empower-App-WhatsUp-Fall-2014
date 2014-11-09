package com.group2.whatsup.Managers;

import android.content.Context;
import android.os.Bundle;

import java.util.HashMap;

public class SettingsManager extends BaseManager {

    //region Singleton Stuff
    private static SettingsManager _instance;
    public static SettingsManager Instance(){
        return _instance;
    }
    public static void Initialize(Bundle state, Context c){
        _instance = new SettingsManager(state, c);
    }
    //endregion

    private HashMap<String, Object> _settings;

    private SettingsManager(Bundle state, Context c){
        super(c);
        _settings = new HashMap<String, Object>();
        populateInitialSettings(state);
    }

    private void populateInitialSettings(Bundle state){

    }


}
