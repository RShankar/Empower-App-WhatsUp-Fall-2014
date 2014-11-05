package com.group2.whatsup.Managers;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseAnalytics;

public class ParseManager {
    private static final String APPLICATION_ID = "cSFUnYQek0bcgdweZK8DTFoNJFMajrjqQ2L4ih6B";
    private static final String CLIENT_KEY = "XZquqoQqitx6Brc4wXrQoJHO29BRFNJeMIHHeZEP";

    //region Singleton Methods.
    private static ParseManager _instance;
    public static ParseManager Instance(){
        return _instance;
    }
    public static void Initialize(Context c){
        if(_instance == null){
            _instance = new ParseManager(c);
        }
    }
    //endregion

    private ParseManager(Context c){
        Parse.initialize(c, APPLICATION_ID, CLIENT_KEY);
    }
}
