package edu.fau.whatsup.Services.Entities;

import edu.fau.whatsup.Debug.Log;
import edu.fau.whatsup.Entities.Authentication.User;
import edu.fau.whatsup.Managers.ToastManager;
import edu.fau.whatsup.ServiceContracts.Entities.IUserService;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class ParseUserService extends BaseParseService implements IUserService {


    @Override
    public User GetByUsername(String username) {
        ParseQuery<ParseObject> query = queryFor(User.ENTITY_NAME).whereEqualTo("userName", username);
        List<ParseObject> objs = doQuery(query);
        return objs.isEmpty() ? null : ParseToEntityConversion.ConvertUser(objs.get(0));
    }

    @Override
    public User GetByEmailAddress(String emailAddress) {
        ParseQuery<ParseObject> query = queryFor(User.ENTITY_NAME).whereEqualTo("emailAddress", emailAddress);
        List<ParseObject> objs = doQuery(query);
        return objs.isEmpty() ? null : ParseToEntityConversion.ConvertUser(objs.get(0));
    }

    @Override
    public User GetById(String id) {
        ParseQuery<ParseObject> query = queryFor(User.ENTITY_NAME);
        ParseObject obj = null;
        try{
             obj = query.get(id);
        }
        catch(ParseException ex){
            Log.Error("Failed to retrieve from parse: {0}", ex.getMessage());
        }

        return obj == null ? null : ParseToEntityConversion.ConvertUser(obj);
    }

    @Override
    public User Save(User arg) {
        ParseObject objToSave = EntityToParseConversion.UserToParseObject(arg);
        final User userRef = arg;
        final ParseObject objRef = objToSave;
        objToSave.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                userRef.set_entityId(objRef.getObjectId());
            }
        });

        return arg;
    }

    @Override
    public boolean Delete(User arg) {
        return false;
    }

    @Override
    public void DeleteAll(){
        ParseQuery<ParseObject> query = queryFor(User.ENTITY_NAME);

        try{
            List<ParseObject> objs = query.find();
            ParseObject.deleteAll(objs);
        }
        catch(Exception ex){
            ToastManager.Instance().SendMessage("Failed to delete all users from parse!", true);
        }


    }
}
