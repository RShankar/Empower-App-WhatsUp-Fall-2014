package com.group2.whatsup.Graphics;

import android.graphics.Color;

public class ColorHelpers {

    public static int GetColor(String hex){
        return Color.parseColor(hex);
    }

    public static HSLColorStruct RGBtoHSL(String hex) {
        return RGBtoHSL(GetHexColorStruct(hex));
    }

    public static String GetHexCompliment(String hex) {
        HSLColorStruct sourceHSL = RGBtoHSL(hex);
        sourceHSL.Hue += 180;
        if(sourceHSL.Hue >= 360) sourceHSL.Hue -= 360;
        HexColorStruct newColor = HSLtoRGB(sourceHSL);
        return newColor.toString();
    }

    public static HSLColorStruct RGBtoHSL(HexColorStruct hex){
        HSLColorStruct retVal = new HSLColorStruct();
        float[] hsv = new float[3];

        Color.RGBToHSV(hex.Red, hex.Green, hex.Blue, hsv);
        retVal.Hue = hsv[0];
        retVal.Saturation = hsv[1];
        retVal.Luminance = hsv[2];

        return retVal;
    }

    public static HexColorStruct HSLtoRGB(HSLColorStruct hsl){
        HexColorStruct retVal = null;

        float[] hslArr = new float[3];
        hslArr[0] = (float)hsl.Hue;
        hslArr[1] = (float)hsl.Saturation;
        hslArr[2] = (float)hsl.Luminance;

        int color = Color.HSVToColor(hslArr);
        retVal = GetHexColorStruct("#" + Integer.toHexString(color));


        return retVal;
    }

    public static HexColorStruct GetHexColorStruct(String hex){
        String rStr = hex.substring(1, 2);
        String gStr = hex.substring(3, 2);
        String bStr = hex.substring(5, 2);

        HexColorStruct retVal = new HexColorStruct();

        retVal.Red = Integer.parseInt(rStr, 16);
        retVal.Green = Integer.parseInt(gStr, 16);
        retVal.Blue = Integer.parseInt(bStr, 16);

        return retVal;
    }
}
