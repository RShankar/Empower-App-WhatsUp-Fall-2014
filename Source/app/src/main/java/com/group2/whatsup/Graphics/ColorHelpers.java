package com.group2.whatsup.Graphics;

import android.graphics.Color;

public class ColorHelpers {

    public static int GetColor(String hex){
        return Color.parseColor(hex);
    }

    public static String GetHexCompliment(String hex) {
        int color = Color.parseColor(hex);
        // get existing colors
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int blue = Color.blue(color);
        int green = Color.green(color);

        // find compliments
        red = (~red) & 0xff;
        blue = (~blue) & 0xff;
        green = (~green) & 0xff;

        HexColorStruct retVal = new HexColorStruct();
        retVal.Red = red;
        retVal.Green = green;
        retVal.Blue = blue;

        return retVal.toString();
    }

    public static HexColorStruct GetHexColorStruct(String hex){
        String rStr = hex.substring(1, 3);
        String gStr = hex.substring(3, 5);
        String bStr = hex.substring(5, 7);

        HexColorStruct retVal = new HexColorStruct();

        retVal.Red = Integer.parseInt(rStr, 16);
        retVal.Green = Integer.parseInt(gStr, 16);
        retVal.Blue = Integer.parseInt(bStr, 16);

        return retVal;
    }
}
