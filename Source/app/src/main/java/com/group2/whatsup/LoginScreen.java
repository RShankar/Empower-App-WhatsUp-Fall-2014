package com.group2.whatsup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InitializeManagers(savedInstanceState);
        super.onCreate(savedInstanceState, R.layout.activity_login_screen);
    }

    @Override
    protected void initializeViewControls() {
        super.initializeViewControls();
        _emailField = (EditText) findViewById(R.id.login_email_field);
        _passwordField = (EditText) findViewById(R.id.login_password_field);
        _loginButton = (Button)findViewById(R.id.login_btnLogIn);
        _signupButton = (Button)findViewById(R.id.login_btnSignup);
    }

    @Override
    protected void setViewTheme() {
        super.setViewTheme();
        final LoginScreen ref = this;

        //Add placeholders.
        _emailField.setHint(R.string.placeholder_email_address);
        _passwordField.setHint(R.string.placeholder_password);
        //_signupButton.setBackgroundColor(SettingsManager.Instance().PrimaryColor());
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
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void attemptLogin(){
        String email = UIUtils.getText(_emailField);
        String password = UIUtils.getText(_passwordField);
        ActionResult<User> authResult = AuthenticationManager.Instance().Authenticate(email, password);
        if(authResult.Success){
            UserManager.Instance().SetActiveUser(authResult.Result);
            ToastManager.Instance().SendMessage("Successfully Logged in", true);

            /* Just temporary to get from one activity to another */
            Intent intent = new Intent(this, MapScreen.class);
            startActivity(intent);
        }
        else{
            ToastManager.Instance().SendMessage(authResult.Message, true);
        }


    }


    /**
     * Initializes all the managers we need for the application.
     */
    private void InitializeManagers(Bundle state){
        Context appContext = getApplicationContext();
        ParseManager.Initialize(appContext);
        SettingsManager.Initialize(state, appContext);
        ToastManager.Initialize(appContext);
        AuthenticationManager.Initialize(appContext);
        UserManager.Initialize(appContext);
        EventManager.Initialize(appContext);
        GPSManager.Initialize(appContext);

    }
}
