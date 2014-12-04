package com.group2.whatsup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.group2.whatsup.Entities.Authentication.User;
import com.group2.whatsup.Helpers.ActionResult;
import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.AuthenticationManager;
import com.group2.whatsup.Managers.Entities.EventManager;
import com.group2.whatsup.Managers.Entities.UserManager;
import com.group2.whatsup.Managers.GPSManager;
import com.group2.whatsup.Managers.ParseManager;
import com.group2.whatsup.Managers.SettingsManager;
import com.group2.whatsup.Managers.ToastManager;


public class LoginScreen extends WUBaseActivity {

    private EditText _emailField;
    private EditText _passwordField;
    private Button _loginButton;
    private Button _signupButton;
    private RelativeLayout _background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(UserManager.Instance().GetActiveUser() != null){
            switchToNextScreen();
        }
        super.onCreate(savedInstanceState, R.layout.activity_login_screen);
    }

    @Override
    protected void initializeViewControls() {
        super.initializeViewControls();
        _emailField = (EditText) findViewById(R.id.login_email_field);
        _passwordField = (EditText) findViewById(R.id.login_password_field);
        _loginButton = (Button)findViewById(R.id.login_btnLogIn);
        _signupButton = (Button)findViewById(R.id.login_btnSignup);
        _background = (RelativeLayout)findViewById(R.id.login_loginBackground);
    }

    @Override
    protected void setViewTheme() {
        super.setViewTheme();
        final LoginScreen ref = this;

        UIUtils.ThemeButton(_signupButton);
        UIUtils.ThemeButton(_loginButton);

        UIUtils.ThemeTextbox(_emailField);
        UIUtils.ThemeTextbox(_passwordField);

        //Temporary debug crap.
        _emailField.setText("test@test.com");
        _passwordField.setText("test");


        //Add placeholders.
        _emailField.setHint(R.string.placeholder_email_address);
        _passwordField.setHint(R.string.placeholder_password);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ref, Signup.class);
                startActivity(i);
            }
        });

        _background.setBackgroundColor(SettingsManager.Instance().SecondaryColor());
    }

    private void attemptLogin(){
        String email = UIUtils.getText(_emailField);
        String password = UIUtils.getText(_passwordField);
        ActionResult<User> authResult = AuthenticationManager.Instance().Authenticate(email, password);
        if(authResult.Success){
            UserManager.Instance().SetActiveUser(authResult.Result);
            ToastManager.Instance().SendMessage("Successfully Logged in", true);
            switchToNextScreen();
        }
        else{
            ToastManager.Instance().SendMessage(authResult.Message, true);
        }
    }

    private void switchToNextScreen(){
        /* Just temporary to get from one activity to another */
        Intent intent = new Intent(this, MapScreen.class);
        startActivity(intent);
    }
}
