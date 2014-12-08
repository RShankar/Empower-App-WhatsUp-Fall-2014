import android.os.Environment;
import android.content.Context;
import android.content.Intent;

//Vardecs
String primaryColor;
int milesToSearch;
String userName;
String passWord;
boolean usersCanCreateEvents;
String createRestrictedMessage;
boolean usersCanSignUp;
String signupRestrictedMessage;
boolean clearAllEvents;
boolean clearAllUsers;

//Set your options up!
void setup(){
  //What the primary color of the applicaton should be!
  primaryColor = "#d23660";
  
  //how many miles to search by
  milesToSearch = 30;
  
  //Default username to fill into the login field.
  userName = "test@test.com";
  
  //Default password to fill into the password field.
  passWord = "test";
  
  //Whether or not the person logging in is allowed to create events.
  usersCanCreateEvents = true;
  
  //The message to display if the user isn't allowed to create an event.
  createRestrictedMessage = "Sorry, but you're not allowed to create events!";
  
  //Whether or not users are allowed to sign up
  usersCanSignUp = false;
  
  //The message to display if the user isn't allowed to sign up.
  signupRestrictedMessage = "No signups allowed!"; 
  
  //Clears all events. Use with caution =)
  clearAllEvents = false;
  
  //Clears all users. This will in turn clear all events.
  //With great power comes great responsibility.
  clearAllUsers = false;
  
  doIntent();
}


//Now boot up our application & have at it!
void doIntent(){
  try{
    Intent wuIntent = getPackageManager().getLaunchIntentForPackage("com.group2.whatsup");
    wuIntent.putExtra("PRIMARY_COLOR", primaryColor);
    wuIntent.putExtra("DISTANCE_PREF", milesToSearch);
    wuIntent.putExtra("USER_NAME", userName);
    wuIntent.putExtra("PASS_WORD", passWord);
    wuIntent.putExtra("ALLOW_CREATE", usersCanCreateEvents);
    wuIntent.putExtra("DISALLOW_CREATE_MESSAGE", createRestrictedMessage);
    wuIntent.putExtra("ALLOW_SIGNUP", usersCanSignUp);
    wuIntent.putExtra("DISALLOW_SIGNUP_MESSAGE", signupRestrictedMessage);
    wuIntent.putExtra("CLEAR_ALL_EVENTS", clearAllEvents);
    wuIntent.putExtra("CLEAR_ALL_USERS", clearAllUsers);
    startActivity(wuIntent);
  }
  catch(Exception ex){
    System.out.println("test");
  }
}
