package com.group2.whatsup.Services.Entities;

import com.group2.whatsup.Entities.Authentication.User;
import com.group2.whatsup.Entities.BaseEntity;
import com.parse.ParseObject;

public class EntityToParseConversion {

    public static ParseObject BaseEntityToParseObject(BaseEntity be){
        ParseObject obj = new ParseObject(be.getEntityName());
        obj.put("Id", be.get_id());
        return obj;
    }

    public static ParseObject UserToParseObject(User u){
        ParseObject obj = BaseEntityToParseObject(u);

        obj.put("userName", u.get_username());
        obj.put("passWord", u.get_password());
        obj.put("age", u.get_age());
        obj.put("emailAddress", u.get_emailAddress());
        obj.put("firstName", u.get_firstName());
        obj.put("lastName", u.get_lastName());


        return obj;
    }
}
