package com.group2.whatsup.Helpers;

//Java really should just implement these as base methods on the number object classes and whatnot. At the very least allow extension methods to make up for shortcomings on their end.
public class MathHelpers {

    public static double Round(double num, int decimalPlaces){
        double precision = Math.pow(10, decimalPlaces);
        double retVal = Math.round(num * precision) / precision;
        return retVal;
    }
}
