package com.group2.whatsup.Helpers;

import java.util.HashMap;

/**
 * Created by Keith on 12/5/2014.
 */
public class IDHelper {
    HashMap _hm = new HashMap();

    public void pushPair(String in_ID, String eventID) {
        _hm.put(in_ID, eventID);
    }

    public String getID (String in_S) {
        String eventID = null;
        eventID = (String) _hm.get(in_S);
        return eventID;
    }
}
