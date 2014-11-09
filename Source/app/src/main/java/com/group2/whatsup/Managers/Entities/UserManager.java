package com.group2.whatsup.Managers.Entities;

import android.content.Context;

import com.group2.whatsup.Entities.Authentication.User;
import com.group2.whatsup.ServiceContracts.Entities.IUserService;
import com.group2.whatsup.Services.Entities.ParseUserService;

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

    public User GetUserById(int id){
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

    public boolean DeleteUser(User u){
        return _userService.Delete(u);
    }

    public void SetActiveUser(User u){
        _user = u;
    }

    public User GetActiveUser(User u){
        return _user;
    }


}
