package com.group2.whatsup;


import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;

import com.group2.whatsup.Managers.SettingsManager;

public class UIUtils {
    //region Text Retrieval Methods
    public static String getText(EditText txt){
        return txt.getText().toString();
    }
    public static int getInt(EditText txt){
        String text = getText(txt);
        return Integer.parseInt(text);
    }
    public static double getDouble(EditText txt){
        String text = getText(txt);
        return Double.parseDouble(text);
    }
    public static float getFloat(EditText txt){
        String text = getText(txt);
        return Float.parseFloat(text);
    }
    public static long getLong(EditText txt){
        String text = getText(txt);
        return Long.parseLong(text);
    }
    public static long getLong(EditText txt, int radix){
        String text = getText(txt);
        return Long.parseLong(text, radix);
    }
    //endregion

    //region Theme Methods
    public static void ThemeButton(Button b){
        b.setBackgroundColor(SettingsManager.Instance().PrimaryColor());
        b.setTextColor(SettingsManager.Instance().SecondaryColor());
    }

    public static void ThemeTextbox(EditText txt){
        txt.setBackgroundColor(Color.WHITE);
        txt.setTextColor(Color.BLACK);
    }
    //endregion
}


