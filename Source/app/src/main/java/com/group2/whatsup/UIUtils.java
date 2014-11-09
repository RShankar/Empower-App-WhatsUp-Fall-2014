package com.group2.whatsup;


import android.widget.EditText;

public class UIUtils {

    public static String getText(EditText txt){
        return txt.getText().toString();
    }
    public static int getInt(EditText txt){
        String text = getText(txt);
        return Integer.parseInt(text);
    }

}
