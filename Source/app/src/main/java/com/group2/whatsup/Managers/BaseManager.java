package com.group2.whatsup.Managers;

import android.content.Context;

public abstract class BaseManager {
    protected Context _context;

    public BaseManager(Context c){
        _context = c;
    }
}
