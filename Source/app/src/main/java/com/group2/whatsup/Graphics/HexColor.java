package com.group2.whatsup.Graphics;

import android.graphics.Color;

public class HexColor {


    public static int GetColor(String hex){
        return Color.parseColor(hex);
    }
}
