package com.group2.whatsup.Entities.Location;

import java.text.MessageFormat;

public class Address {
    public String StreetLine1;
    public String StreetLine2;
    public String City;
    public String State;
    public String PostalCode;

    public String GeoCoderFriendlyName(){
        return MessageFormat.format(
                "{0} {1} {2}, {3} {4}",
                /*00*/StreetLine1 == null ? "" : StreetLine1,
                /*01*/StreetLine2 == null ? "" : StreetLine2,
                /*02*/City == null ? "" : City,
                /*03*/State == null ? "" : State,
                /*04*/PostalCode == null ? "" : PostalCode
        );
    }
}
