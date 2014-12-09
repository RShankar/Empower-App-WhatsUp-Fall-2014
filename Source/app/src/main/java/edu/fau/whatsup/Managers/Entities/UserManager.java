package edu.fau.whatsup.Managers.Entities;

import android.content.Context;

import edu.fau.whatsup.Debug.Log;
import edu.fau.whatsup.Entities.Authentication.User;
import edu.fau.whatsup.Managers.GPSManager;
import edu.fau.whatsup.ServiceContracts.Entities.IUserService;
import edu.fau.whatsup.Services.Entities.ParseUserService;

public class UserManager {
    //region Singleton BS
    private static UserManager _instance;
    public static void Initialize(Context c){
        if(_instance == null) _instance = new UserManager(c);
    }
    public static UserManager Instance(){
        return _instance;
    }
    //endregion

    private Context _context;
    private IUserService _userService;
    private User _user;
    private UserManager(Context c){
        _context = c;
        _userService = new ParseUserService();
    }

    public User GetUserById(String id){
        return _userService.GetById(id);
    }

    public User GetUserByUsername(String username){
        return _userService.GetByUsername(username);
    }

    public User GetByEmailAddress(String emailAddress){
        return _userService.GetByEmailAddress(emailAddress);
    }

    public User SaveUser(User u){
        return _userService.Save(u);
    }

    public void SaveInThread(final User u){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.Info("Saving user's updated location in a background thread.");
                _userService.Save(u);
            }
        });
        t.setName("User Save Background Thread");
        t.run();

    }

    public boolean DeleteUser(User u){
        return _userService.Delete(u);
    }

    public void SetActiveUser(final User u){
        if(GPSManager.Instance().HasLocation()){
            u.set_lastKnownLocation(GPSManager.Instance().CurrentLocation());
            SaveUser(u);
        }
        else{
            if(u.get_lastKnownLocation() != null){
                GPSManager.Instance().OverrideCurrentLocation(u.get_lastKnownLocation());
            }

            GPSManager.Instance().WhenLocationSet(new Runnable() {
                @Override
                public void run() {
                    u.set_lastKnownLocation(GPSManager.Instance().CurrentLocation());
                    SaveUser(u);
                }
            });
        }
        _user = u;
    }

    public User GetActiveUser(){
        return _user;
    }

    public void DeleteAll(){
        _userService.DeleteAll();
    }
}
