package com.group2.whatsup;

import android.app.Activity;
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
import com.group2.whatsup.Managers.ToastManager;


public class Signup extends WUBaseActivity {

    private EditText _emailBox;
    private EditText _nameBox;
    private EditText _passwordBox;
    private Button _signupButton;
    private EditText _ageBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_signup);
    }

    @Override
    protected void initializeViewControls(){
        _emailBox = (EditText) findViewById(R.id.signup_email);
        _passwordBox = (EditText) findViewById(R.id.signup_password);
        _signupButton = (Button) findViewById(R.id.signup_btnSignUp);
        _nameBox = (EditText) findViewById(R.id.signup_name);
        _ageBox = (EditText) findViewById(R.id.signup_age);
    }

    @Override
    protected void setViewTheme(){
        _emailBox.setHint(R.string.placeholder_email_address);
        _passwordBox.setHint(R.string.placeholder_password);

        UIUtils.ThemeButtons(_signupButton);

        final Signup ref = this;
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User();

                int age = UIUtils.getInt(_ageBox);
                String name = UIUtils.getText(_nameBox);
                String email = UIUtils.getText(_emailBox);
                String password = UIUtils.getText(_passwordBox);

                newUser.set_firstName(name);
                newUser.set_lastName(name);
                newUser.set_username(email);
                newUser.set_emailAddress(email);
                newUser.set_age(age);
                newUser.set_password(password);

                ActionResult<User> signupResult = AuthenticationManager.Instance().SignUp(newUser);

                if(signupResult.Success){
                    Intent i = new Intent(ref, EventList.class);
                    startActivity(i);
                }
                else{
                    ToastManager.Instance().SendMessage(signupResult.Message, true);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
