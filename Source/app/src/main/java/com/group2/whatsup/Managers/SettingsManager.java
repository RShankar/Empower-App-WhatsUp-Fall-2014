package com.group2.whatsup.Managers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.EventAddEdit;
import com.group2.whatsup.Graphics.ColorHelpers;
import com.group2.whatsup.LoginScreen;
import com.group2.whatsup.Managers.Entities.EventManager;

import java.util.HashMap;

public class SettingsManager extends BaseManager {

    //region VarDecs avail from other applications
    static final String PRIMARY_COLOR_KEY = "PRIMARY_COLOR";
    //Original value:
    //Good value: #efefef
    static final String PRIMARY_COLOR_DEFAULT_VALUE = "#d23660  ";

    static final String DISTANCE_PREF_KEY = "DISTANCE_PREF";
    static final int DISTANCE_PREF_DEFAULT_VALUE = 30;

    static final String START_USERNAME_KEY = "USER_NAME";
    static final String START_USERNAME_DEFAULT_VALUE = "";

    static final String START_PASSWORD_KEY = "PASS_WORD";
    static final String START_PASSWORD_DEFAULT_VALUE = "";

    static final String ALLOW_USER_TO_CREATE_KEY = "ALLOW_CREATE";
    static final Boolean ALLOW_USER_TO_CREATE_DEFAULT_VALUE = true;

    static final String DISALLOW_CREATION_MESSAGE_KEY = "DISALLOW_CREATE_MESSAGE";
    static final String DISALLOW_CREATION_MESSAGE_DEFAULT_VALUE = "Sorry, you are not permitted to create new events.";

    static final String ALLOW_USER_TO_SIGN_UP_KEY = "ALLOW_SIGNUP";
    static final Boolean ALLOW_USER_TO_SIGN_UP_DEFAULT_VALUE = true;

    static final String DISALLOW_SIGNUP_MESSAGE_KEY = "DISALLOW_SIGNUP_MESSAGE";
    static final String DISALLOW_SIGNUP_MESSAGE_DEFAULT_VALUE = "Sorry, signups are disabled for the moment.";

    static final String SHOULD_CLEAR_EVENTS_KEY = "CLEAR_ALL_EVENTS";
    static final Boolean SHOULD_CLEAR_EVENTS_DEFAULT_VALUE = false;

    static final String SHOULD_CLEAR_USERS_KEY = "CLEAR_ALL_USERS";
    static final Boolean SHOULD_CLEAR_USERS_DEFAULT_VALUE = false;
    //endregion

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

    public static void Reinitialize(Bundle state, Context c){
        _instance = new SettingsManager(state, c);
    }
    //endregion

    private SettingsManager(Bundle state, Context c){
        super(c);
        _settings = new HashMap<String, Object>();
        populateInitialSettings(state);
    }

    private HashMap<String, Object> _settings;

    private void populateInitialSettings(Bundle state) {
        boolean retVal = true;

        retVal &= doPut(PRIMARY_COLOR_KEY, state, PRIMARY_COLOR_DEFAULT_VALUE);
        retVal &= doPut(DISTANCE_PREF_KEY, state, DISTANCE_PREF_DEFAULT_VALUE);
        retVal &= doPut(START_USERNAME_KEY, state, START_USERNAME_DEFAULT_VALUE);
        retVal &= doPut(START_PASSWORD_KEY, state, START_PASSWORD_DEFAULT_VALUE);
        retVal &= doPut(ALLOW_USER_TO_CREATE_KEY, state, ALLOW_USER_TO_CREATE_DEFAULT_VALUE);
        retVal &= doPut(DISALLOW_CREATION_MESSAGE_KEY, state, DISALLOW_CREATION_MESSAGE_DEFAULT_VALUE);
        retVal &= doPut(ALLOW_USER_TO_SIGN_UP_KEY, state, ALLOW_USER_TO_SIGN_UP_DEFAULT_VALUE);
        retVal &= doPut(DISALLOW_SIGNUP_MESSAGE_KEY, state, DISALLOW_SIGNUP_MESSAGE_DEFAULT_VALUE);
        retVal &= doPut(SHOULD_CLEAR_EVENTS_KEY, state, SHOULD_CLEAR_EVENTS_DEFAULT_VALUE);
        retVal &= doPut(SHOULD_CLEAR_USERS_KEY, state, SHOULD_CLEAR_USERS_DEFAULT_VALUE);

        if(!retVal){
            Log.Info("Failed to load some settings");
        }
    }

    private boolean doPut(String key, Bundle state, Object defVal){
        if(state != null){
            try{
                Object resultantVal = state.get(key);
                _settings.put(key, resultantVal == null ? defVal : resultantVal);
                Log.Info("Loaded val {0} in for key: {1}, received {2}", _settings.get(key), key, resultantVal);
                return true;
            }
            catch(Exception ex){
                _settings.put(key, defVal);
                ToastManager.Instance().SendMessage("Failed to load value for " + key + ", please check your values!", true);
            }
        }
        else{
            _settings.put(key, defVal);
        }

        return false;
    }


    //region Primary Color
    public String PrimaryColorHex(){
        return _settings.containsKey(PRIMARY_COLOR_KEY) ? _settings.get(PRIMARY_COLOR_KEY).toString() : PRIMARY_COLOR_DEFAULT_VALUE;
    }
    public int PrimaryColor(){
        int retVal = 0;
        try{
            retVal = ColorHelpers.GetColor(PrimaryColorHex());
        }
        catch(Exception ex){
            ToastManager.Instance().SendMessage("Failed to parse your color, Android's color library can be a little wonky sometimes. Try another!", true);
            _settings.put(PRIMARY_COLOR_KEY, PRIMARY_COLOR_DEFAULT_VALUE);
            retVal = ColorHelpers.GetColor(PrimaryColorHex());
        }

        return retVal;

    }
    //endregion

    //region Secondary Color
    public String SecondaryColorHex(){
        PrimaryColor();
        return ColorHelpers.GetHexCompliment(PrimaryColorHex());
    }
    public int SecondaryColor(){
        PrimaryColor();
        return ColorHelpers.GetColor(SecondaryColorHex());
    }
    //endregion

    //region PreferredDistance
    public int DistancePreference(){
        return _settings.containsKey(DISTANCE_PREF_KEY) ? (Integer) _settings.get(DISTANCE_PREF_KEY) : DISTANCE_PREF_DEFAULT_VALUE;
    }

    public void SetDistancePreference(int distance){
        _settings.put(DISTANCE_PREF_KEY, distance);
    }
    //endregion

    //region Default Username
    public String DefaultUsername(){
        return _settings.get(START_USERNAME_KEY).toString();
    }
    //endregion

    //region Default Password
    public String DefaultPassword(){
        return _settings.get(START_PASSWORD_KEY).toString();
    }
    //endregion

    //region User Permissions
    public Boolean UserCanCreate(){
        return (Boolean)_settings.get(ALLOW_USER_TO_CREATE_KEY);
    }
    public Boolean UserCanSignUp(){
        return (Boolean)_settings.get(ALLOW_USER_TO_SIGN_UP_KEY);
    }
    public void SendCannotCreateMessage(){
        ToastManager.Instance().SendMessage(_settings.get(DISALLOW_CREATION_MESSAGE_KEY).toString(), true);
    }
    public void SendCannotSignupMessage(){
        ToastManager.Instance().SendMessage(_settings.get(DISALLOW_SIGNUP_MESSAGE_KEY).toString(), true);
    }
    //endregion

    //region Clearing Stuff
    public Boolean ShouldClearEvents(){
        return ShouldClearUsers() || (Boolean)_settings.get(SHOULD_CLEAR_EVENTS_KEY);
    }

    public Boolean ShouldClearUsers(){
        return (Boolean)_settings.get(SHOULD_CLEAR_USERS_KEY);
    }
    //endregion


}
