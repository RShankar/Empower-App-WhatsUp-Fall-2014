package com.group2.whatsup.Services.Entities;

import com.group2.whatsup.Debug.Log;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseParseService {

    protected ParseQuery<ParseObject> queryFor(String name){
        return ParseQuery.getQuery(name);
    }

    protected List<ParseObject> doQuery(ParseQuery<ParseObject> query){
        List<ParseObject> objs = new ArrayList<ParseObject>();

        try{
            objs = query.find();
        }
        catch(ParseException ex){
            Log.Error("Failed to communicate with Parse: {0}", ex);
        }

        return objs;
    }

    protected <T> T firstOrDefault(List<T> items){
        return items.isEmpty() ? null : items.get(0);
    }
}
