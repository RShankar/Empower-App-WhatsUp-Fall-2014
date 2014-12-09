package com.group2.whatsup;

import android.app.Activity;
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
import com.group2.whatsup.Helpers.Validate;
import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.AuthenticationManager;
import com.group2.whatsup.Managers.Entities.UserManager;
import com.group2.whatsup.Managers.GPSManager;
import com.group2.whatsup.Managers.SettingsManager;
import com.group2.whatsup.Managers.ToastManager;

import java.util.ArrayList;


public class Signup extends WUBaseActivity {

    private EditText _emailBox;
    private EditText _firstName;
    private EditText _lastName;
    private EditText _passwordBox;
    private Button _signupButton;
    private EditText _ageBox;
    private RelativeLayout _background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_signup);
    }

    @Override
    protected void initializeViewControls(){
        _emailBox = (EditText) findViewById(R.id.signup_email);
        _passwordBox = (EditText) findViewById(R.id.signup_password);
        _signupButton = (Button) findViewById(R.id.signup_btnSignUp);
        _firstName = (EditText) findViewById(R.id.signup_firstName);
        _lastName = (EditText) findViewById(R.id.signup_lastName);
        _ageBox = (EditText) findViewById(R.id.signup_age);
        _background = (RelativeLayout) findViewById(R.id.signup_background);
    }

    @Override
    protected void setViewTheme(){
        _emailBox.setHint(R.string.placeholder_email_address);
        _passwordBox.setHint(R.string.placeholder_password);

        UIUtils.ThemeButtons(_signupButton);
        _background.setBackgroundColor(SettingsManager.Instance().SecondaryColor());

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }


    private User entityFromFields(){
        User retVal = new User();

        int age = UIUtils.getInt(_ageBox);
        String firstName = UIUtils.getText(_firstName);
        String lastName = UIUtils.getText(_lastName);
        String userName = UIUtils.getText(_emailBox);
        String password = UIUtils.getText(_passwordBox);
        String emailAddress = userName;

        retVal.set_firstName(firstName);
        retVal.set_lastName(lastName);
        retVal.set_username(userName);
        retVal.set_password(password);
        retVal.set_emailAddress(emailAddress);
        retVal.set_age(age);

        if(GPSManager.Instance().HasLocation()){
            retVal.set_lastKnownLocation(GPSManager.Instance().CurrentLocation());
        }

        return retVal;
    }

    private void save(){
        User newUser = entityFromFields();

        if(validate(newUser)){
            ActionResult<User> signupResult = AuthenticationManager.Instance().SignUp(newUser);

            if(signupResult.Success){
                UserManager.Instance().SetActiveUser(signupResult.Result);
                Intent i = new Intent(Signup.this, MapScreen.class);
                startActivity(i);
            }
            else{
                ToastManager.Instance().SendMessage(signupResult.Message, true);
            }
        }
    }

    private boolean validate(User u){
        boolean retVal = true;
        StringBuilder valMsg = new StringBuilder();
        ArrayList<Validate.Result> res = new ArrayList<Validate.Result>();
        res.add(Validate.Username(u.get_username()));
        res.add(Validate.Password(UIUtils.getText(_passwordBox)));
        res.add(Validate.FirstName(u.get_firstName()));
        res.add(Validate.LastName(u.get_lastName()));
        res.add(Validate.Age(u.get_age()));


        for(Validate.Result r : res){
            if(!r.valid){
                retVal = false;
                valMsg.append(r.message + "\n");
            }
        }

        if(!retVal){
            ToastManager.Instance().SendMessage(valMsg.toString(), true);
        }


        return retVal;
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
