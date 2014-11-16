package com.group2.whatsup.Managers;

import android.content.Context;

import com.group2.whatsup.Entities.Authentication.User;
import com.group2.whatsup.Helpers.ActionResult;
import com.group2.whatsup.Managers.Entities.UserManager;
import com.group2.whatsup.Security.Encoding;

public class AuthenticationManager extends BaseManager {
    //region Singleton BS
    private static AuthenticationManager _instance;
    public static void Initialize(Context c){
        if(_instance == null) _instance = new AuthenticationManager(c);
    }
    public static AuthenticationManager Instance(){
        return _instance;
    }
    //endregion

    private Context _context;
    public AuthenticationManager(Context c){
        super(c);
    }

    public ActionResult<User> Authenticate(String email, String password){
        ActionResult<User> result = new ActionResult<User>();

        User attemptingUser = UserManager.Instance().GetByEmailAddress(email);

        //The passwords match
        if(attemptingUser != null){
            if(attemptingUser.get_password().equals(Encoding.SHAHash(password))){
                result.Success = true;
                result.Result = attemptingUser;
                UserManager.Instance().SetActiveUser(attemptingUser);
            }
            else result.Message = "Invalid Password.";
        }
        else result.Message = "Email Address not found.";


        return result;
    }

    public ActionResult<User> SignUp(User attemptingUser){
        ActionResult<User> result = new ActionResult<User>();
        User existingUser = UserManager.Instance().GetByEmailAddress(attemptingUser.get_emailAddress());

        if(existingUser == null){
            User finalUser = UserManager.Instance().SaveUser(attemptingUser);
            result.Success = true;
            result.Result = finalUser;
        }
        else{
            result.Message = "That email address already exists! Please try a different one.";
        }

        return result;
    }
}
