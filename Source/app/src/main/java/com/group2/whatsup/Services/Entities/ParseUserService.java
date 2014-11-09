package com.group2.whatsup.Services.Entities;

import com.group2.whatsup.Debug.Log;
import com.group2.whatsup.Entities.Authentication.User;
import com.group2.whatsup.ServiceContracts.Entities.IUserService;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ParseUserService extends BaseParseService implements IUserService {

    private final static String _entityName = "WUUser";

    @Override
    public User GetByUsername(String username) {
        ParseQuery<ParseObject> query = queryFor(_entityName).whereEqualTo("userName", username);
        List<ParseObject> objs = doQuery(query);
        return objs.isEmpty() ? null : ParseToEntityConversion.ConvertUser(objs.get(0));
    }

    @Override
    public User GetByEmailAddress(String emailAddress) {
        ParseQuery<ParseObject> query = queryFor(_entityName).whereEqualTo("emailAddress", emailAddress);
        List<ParseObject> objs = doQuery(query);
        return objs.isEmpty() ? null : ParseToEntityConversion.ConvertUser(objs.get(0));
    }

    @Override
    public User GetById(int id) {
        ParseQuery<ParseObject> query = queryFor(_entityName).whereEqualTo("id", id);
        List<ParseObject> objs = doQuery(query);
        return objs.isEmpty() ? null : ParseToEntityConversion.ConvertUser(objs.get(0));
    }

    @Override
    public User Save(User arg) {
        ParseObject objToSave = EntityToParseConversion.UserToParseObject(arg);

        try{
            objToSave.save();
            arg.set_id(objToSave.getInt("Id"));
        }
        catch(ParseException ex){
            Log.Error("Error saving user: {0}", ex.getMessage());
        }

        return arg;
    }

    @Override
    public boolean Delete(User arg) {
        return false;
    }
}
