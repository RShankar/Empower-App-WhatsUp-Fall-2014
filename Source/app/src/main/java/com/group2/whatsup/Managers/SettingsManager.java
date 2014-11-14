package com.group2.whatsup.Managers;

import android.content.Context;
import android.os.Bundle;

import com.group2.whatsup.Graphics.HexColor;

import java.util.HashMap;

public class SettingsManager extends BaseManager {

    static final String PRIMARY_COLOR_KEY = "";
    static final String PRIMARY_COLOR_DEFAULT_VALUE = "#FF0000";

    //region Singleton Stuff
    private static SettingsManager _instance;
    public static SettingsManager Instance(){
        return _instance;
    }
    public static void Initialize(Bundle state, Context c){
        if(_instance == null) {
            _instance = new SettingsManager(state, c);
        }
    }
    //endregion

    private SettingsManager(Bundle state, Context c){
        super(c);
        _settings = new HashMap<String, Object>();
        populateInitialSettings(state);
    }


    private HashMap<String, Object> _settings;
    private String _defaultPrimaryColor;



    private void populateInitialSettings(Bundle state) {
        doPut(PRIMARY_COLOR_KEY, state.getString(PRIMARY_COLOR_KEY), PRIMARY_COLOR_DEFAULT_VALUE);
    }

    private void doPut(String key, Object value, Object defVal){
        _settings.put(key, value == null ? defVal : value);
    }


    //region Primary Color
    public String PrimaryColorHex(){
        return _settings.containsKey(PRIMARY_COLOR_KEY) ? _settings.get(PRIMARY_COLOR_KEY).toString() : PRIMARY_COLOR_DEFAULT_VALUE;
    }
    public int PrimaryColor(){
        return HexColor.GetColor(PrimaryColorHex());
    }
    //endregion



}
