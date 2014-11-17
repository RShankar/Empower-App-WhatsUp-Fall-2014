package com.group2.whatsup.Graphics;

import android.graphics.Color;

public class HexColor {

    public static int GetColor(String hex){
        return Color.parseColor(hex);
    }

    public static HSLColorStruct RGBtoHSL(String hex) {
        return RGBtoHSL(GetHexColorStruct(hex));
    }

    public static HSLColorStruct RGBtoHSL(HexColorStruct hex){
        HSLColorStruct retVal = new HSLColorStruct();

        double h,s,l;
        int r,g,b,max, min;
        r = hex.Red / 255; g = hex.Green / 255; b = hex.Blue / 255;

        max = Math.max(r,g); max = Math.max(max, b);

        min = Math.min(r,g); min = Math.min(min, b);

        l = (max + min) / 2;

        if(max == min){
            h = 0;
            s = 0;
        }
        else{
            double d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);

            if(max == r){
                h = (g - b) / d + (g < b ? 6 : 0);
            }
            else if (max == g){
                h = (b - r) / d + 2;
            }
            else{
                h = (r - g) / d + 4;
            }

            h /= 6;
        }

        retVal.Hue = h;
        retVal.Saturation = s;
        retVal.Luminance = l;

        return retVal;
    }

    /*
    //TODO: Do more work on this - need to work out a reversal from RGB to HSL.
    public static HexColorStruct HSLtoRGB(HSLColorStruct hsl){
        int r;
        int g;
        int b;

        if(hsl.Saturation == 0){
            r = g = b = (int)hsl.Luminance;
        }
        else{

        }
    }

    public static HexColorStruct GetComplimentForRGB(String hex){
        HSLColorStruct fromHsl = RGBtoHSL(hex);

        double H = fromHsl.Hue > 180 ? fromHsl.Hue - 180 : fromHsl.Hue + 180;


    }
    */


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
