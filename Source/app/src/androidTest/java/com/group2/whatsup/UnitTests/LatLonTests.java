package com.group2.whatsup.UnitTests;

import android.test.InstrumentationTestCase;

import com.group2.whatsup.Entities.Location.LatLon;

public class LatLonTests extends InstrumentationTestCase {

    public void testEqualsWorksToThreeDecimalPlaces(){
        LatLon first = new LatLon(10.1011, 10.1012);
        LatLon second = new LatLon(10.1012, 10.1013);
        boolean state = first.equals(second);
        assertTrue(state);
    }

}
