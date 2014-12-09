package edu.fau.whatsup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import edu.fau.whatsup.Debug.Log;
import edu.fau.whatsup.Entities.Authentication.User;
import edu.fau.whatsup.Helpers.ActionResult;
import edu.fau.whatsup.Interop.WUBaseActivity;
import edu.fau.whatsup.Managers.AuthenticationManager;
import edu.fau.whatsup.Managers.Entities.EventManager;
import edu.fau.whatsup.Managers.Entities.UserManager;
import edu.fau.whatsup.Managers.SettingsManager;
import edu.fau.whatsup.Managers.ToastManager;


public class LoginScreen extends WUBaseActivity {

    private EditText _emailField;
    private EditText _passwordField;
    private Button _loginButton;
    private Button _signupButton;
    private RelativeLayout _background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle test = getIntent().getExtras();
        if(test != null){
            Log.Info("Reinitializing SettingsManager");
            SettingsManager.Reinitialize(test, getApplicationContext());
        }
        else{
            Log.Info("Bundle null. Keeping default values.");
        }

        clearWorkflow();

        if(UserManager.Instance().GetActiveUser() != null){
            changeActivity(MapScreen.class);
        }
        super.onCreate(savedInstanceState, R.layout.activity_login_screen);
    }


    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.login_loadingPanel).setVisibility(View.INVISIBLE);
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

        UIUtils.ThemeButtons(_signupButton, _loginButton);
        UIUtils.ThemeTextboxes(_emailField, _passwordField);

        //Temporary debug crap.
        _emailField.setText(SettingsManager.Instance().DefaultUsername());
        _passwordField.setText(SettingsManager.Instance().DefaultPassword());

        _emailField.setText("phalpin@fau.edu");
        _passwordField.setText("test");


        //Add placeholders.
        _emailField.setHint(R.string.placeholder_email_address);
        _passwordField.setHint(R.string.placeholder_password);

        //findViewById(R.id.login_loadingPanel).setVisibility(View.INVISIBLE);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SettingsManager.Instance().UserCanSignUp()){
                    Intent i = new Intent(ref, Signup.class);
                    startActivity(i);
                }
                else{
                    SettingsManager.Instance().SendCannotSignupMessage();
                }

            }
        });

        _background.setBackgroundColor(SettingsManager.Instance().SecondaryColor());
    }


    private void attemptLogin(){
        // bring back loading bar
        findViewById(R.id.login_loadingPanel).setVisibility(View.VISIBLE);
        findViewById(R.id.login_loadingPanel).bringToFront();
        String email = UIUtils.getText(_emailField);
        String password = UIUtils.getText(_passwordField);
        ActionResult<User> authResult = AuthenticationManager.Instance().Authenticate(email, password);
        if(authResult.Success){
            UserManager.Instance().SetActiveUser(authResult.Result);
            ToastManager.Instance().SendMessage("Successfully Logged in", true);
            changeActivity(MapScreen.class);
        }
        else{
            ToastManager.Instance().SendMessage(authResult.Message, true);
            // get rid of loading bar if false
            findViewById(R.id.login_loadingPanel).setVisibility(View.INVISIBLE);
        }

    }

    private void clearWorkflow(){
        clearEventsWorkflow();
    }

    private void clearEventsWorkflow(){
        if(SettingsManager.Instance().ShouldClearEvents()){
            DialogInterface.OnClickListener dLog = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch(which){
                        case DialogInterface.BUTTON_POSITIVE:
                            EventManager.Instance().DeleteAll();
                            Log.Info("GO GO GO GO GO CLEAR IT GO GO GO GO GO GO");
                            clearUsersWorkflow();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            ToastManager.Instance().SendMessage("Did not clear any events", true);
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
            builder.setMessage("Are you sure you want to delete ALL events?");
            builder.setPositiveButton("Yes", dLog);
            builder.setNegativeButton("No", dLog);
            builder.show();
        }
    }

    private void clearUsersWorkflow(){
        if(SettingsManager.Instance().ShouldClearUsers()){
            DialogInterface.OnClickListener dLog = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch(which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //UserManager.Instance().DeleteAll();
                            Log.Info("GO GO GO GO GO CLEAR IT GO GO GO GO GO GO");
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            ToastManager.Instance().SendMessage("Did not clear any users", true);
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
            builder.setMessage("Are you sure you want to delete ALL users?");
            builder.setPositiveButton("Yes", dLog);
            builder.setNegativeButton("No", dLog);
            builder.show();
        }
    }
}
