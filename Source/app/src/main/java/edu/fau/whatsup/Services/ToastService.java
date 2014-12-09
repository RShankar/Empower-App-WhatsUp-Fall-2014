package edu.fau.whatsup.Services;

import android.content.Context;
import android.widget.Toast;

import edu.fau.whatsup.ServiceContracts.IToastService;


public class ToastService extends BaseService implements IToastService {


    public ToastService(Context c)
    {
        super(c);
    }



    @Override
    public void SendMessage(String message, boolean longDuration) {
        Toast t = Toast.makeText(_context, message, longDuration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        t.show();
    }

}
