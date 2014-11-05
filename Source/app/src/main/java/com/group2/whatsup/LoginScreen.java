package com.group2.whatsup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.ParseManager;
import com.group2.whatsup.Managers.SettingsManager;
import com.group2.whatsup.Managers.ToastManager;


public class LoginScreen extends WUBaseActivity {

    private EditText _emailField;
    private EditText _passwordField;
    private Button _loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_login_screen);
        InitializeManagers(savedInstanceState);
    }

    @Override
    protected void initializeViewControls() {
        super.initializeViewControls();
        _emailField = (EditText) findViewById(R.id.login_email_field);
        _passwordField = (EditText) findViewById(R.id.login_password_field);
        _loginButton = (Button)findViewById(R.id.login_btnLogIn);
    }

    @Override
    protected void setViewTheme() {
        super.setViewTheme();

        //Add placeholders.
        _emailField.setHint(R.string.placeholder_email_address);
        _passwordField.setHint(R.string.placeholder_password);
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
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
        String msg = UIUtils.getText(_emailField);
        ToastManager.Instance().SendMessage(msg, true);

        /* Just temporary to get from one activity to another */
        Intent intent = new Intent(this, MapScreen.class);
        startActivity(intent);
    }


    /**
     * Initializes all the managers we need for the application.
     */
    private void InitializeManagers(Bundle state){
        Context appContext = getApplicationContext();
        ParseManager.Initialize(appContext);
        SettingsManager.Initialize(state);
        ToastManager.Initialize(appContext);
    }
}
