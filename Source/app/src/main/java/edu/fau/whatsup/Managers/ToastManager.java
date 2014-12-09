package edu.fau.whatsup.Managers;


import android.content.Context;

import edu.fau.whatsup.ServiceContracts.IToastService;
import edu.fau.whatsup.Services.ToastService;

public class ToastManager extends BaseManager {

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
        super(c);
        _service = new ToastService(c);
    }

    public void SendMessage(String message, boolean longDuration){
        _service.SendMessage(message, longDuration);
    }

}
