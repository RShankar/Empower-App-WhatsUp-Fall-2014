package com.group2.whatsup.Services.Entities;

import com.group2.whatsup.Entities.Authentication.User;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class ParseToEntityConversion {

    //region User Conversions.
    public static User ConvertUser(ParseObject obj){
        User u = new User();

        u.set_age(obj.getInt("age"));
        u.set_emailAddress(obj.getString("emailAddress"));
        u.set_firstName(obj.getString("firstName"));
        u.set_lastName(obj.getString("lastName"));
        u.set_username(obj.getString("userName"));
        u.set_password(obj.getString("passWord"), false);
        u.set_id(obj.getInt("Id"));

        return u;
    }
    public static ArrayList<User> ConvertUsers(List<ParseObject> obj){
        ArrayList<User> retVal = new ArrayList<User>();

        for(ParseObject p : obj){
            retVal.add(ConvertUser(p));
        }

        return retVal;
    }
    //endregion

}
