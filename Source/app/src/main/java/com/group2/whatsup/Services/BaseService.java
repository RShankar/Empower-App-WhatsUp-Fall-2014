package com.group2.whatsup.Services;


import android.content.Context;

public abstract class BaseService {
    protected Context _context;

    public BaseService(Context c){
        _context = c;
    }
}
