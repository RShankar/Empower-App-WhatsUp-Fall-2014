package com.group2.whatsup.Managers;


import android.content.Context;

import com.group2.whatsup.ServiceContracts.IToastService;
import com.group2.whatsup.Services.ToastService;

public class ToastManager {

    //region Singleton BS
    private static ToastManager _instance;

    public static void Initialize(Context c){
        if(_instance == null) _instance = new ToastManager(c);
    }

    public static ToastManager Instance(){
        return _instance;
    }
    //endregion

    private Context _context;
    private IToastService _service;

    private ToastManager(Context c){
        _context = c;
        initializeServices();
    }

    private void initializeServices(){
        _service = new ToastService(_context);
    }

    public void SendMessage(String message, boolean longDuration){
        _service.SendMessage(message, longDuration);
    }

}
