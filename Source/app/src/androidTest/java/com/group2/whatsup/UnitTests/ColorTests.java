package com.group2.whatsup.UnitTests;

import android.test.InstrumentationTestCase;

import com.group2.whatsup.Graphics.*;

public class ColorTests extends InstrumentationTestCase {

    public void testRetrieveHexColorStruct(){
        String colorHex = "#801515";
        HexColorStruct result = ColorHelpers.GetHexColorStruct(colorHex);
        assertEquals(result.Red, 128);
        assertEquals(result.Green, 21);
        assertEquals(result.Blue, 21);

        assertEquals(result.toString(), colorHex);
    }

    public void testRetrieveColorCompliment(){
        HexColorStruct colorHex = new HexColorStruct();
        colorHex.Red = 128;
        colorHex.Green = 21;
        colorHex.Blue = 21;

        String initial = "#801515";
        String expected = "#7FEAEA";

        String oppCol = ColorHelpers.GetHexCompliment(colorHex.toString());
        assertEquals(expected, oppCol);

        String secOppCol = ColorHelpers.GetHexCompliment(oppCol);
        assertEquals(initial, secOppCol);
    }

}
