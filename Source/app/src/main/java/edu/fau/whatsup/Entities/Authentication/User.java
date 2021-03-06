package edu.fau.whatsup.Entities.Authentication;

import edu.fau.whatsup.Entities.BaseEntity;
import edu.fau.whatsup.Entities.Location.LatLon;
import edu.fau.whatsup.Security.Encoding;

public class User extends BaseEntity {

    public static final String ENTITY_NAME = "WUUser";

    public User(){
        super(ENTITY_NAME);
    }

    //region First Name
    private String _firstName;
    public String get_firstName() {
        return _firstName;
    }
    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }
    //endregion

    //region Last Name
    private String _lastName;
    public String get_lastName() {
        return _lastName;
    }
    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }
    //endregion

    //region Age
    private int _age;
    public int get_age() {
        return _age;
    }
    public void set_age(int _age) {
        this._age = _age;
    }
    //endregion

    //region Username
    private String _username;
    public String get_username() {
        return _username;
    }
    public void set_username(String _username) {
        this._username = _username;
    }
    //endregion

    //region Password
    private String _password;
    public String get_password() {
        return _password;
    }
    public void set_password(String _password) {
        this._password = Encoding.SHAHash(_password);
    }
    public void set_password(String _password, boolean doEncryption){
        this._password = doEncryption ? Encoding.SHAHash(_password) : _password;
    }
    //endregion

    //region Email Address
    private String _emailAddress;
    public String get_emailAddress() {
        return _emailAddress;
    }
    public void set_emailAddress(String _emailAddress) {
        this._emailAddress = _emailAddress;
    }
    //endregion

    //region Last Known Location
    private LatLon _lastKnownLocation;
    public LatLon get_lastKnownLocation(){
        return _lastKnownLocation;
    }
    public void set_lastKnownLocation(LatLon _lastKnownLocation){
        this._lastKnownLocation = _lastKnownLocation;
    }
    public boolean has_lastKnownLocation(){ return _lastKnownLocation != null; }
    //endregion

}
