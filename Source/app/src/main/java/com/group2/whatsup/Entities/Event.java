package com.group2.whatsup.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.group2.whatsup.Entities.Authentication.User;
import com.group2.whatsup.Entities.Location.Address;
import com.group2.whatsup.Entities.Location.LatLon;

import java.util.ArrayList;
import java.util.Date;

public class Event extends BaseEntity {

    public static final String ENTITY_NAME = "WUEvent";

    public Event(){
        super(ENTITY_NAME);
        _imageNames = new ArrayList<String>();
        _attendees = new ArrayList<User>();
    }

    //region Title
    private String _title;
    public String get_title() {
        return _title;
    }
    public void set_title(String _title) {
        this._title = _title;
    }
    //endregion

    //region Image Names
    private ArrayList<String> _imageNames;
    public ArrayList<String> get_imageNames() {
        return _imageNames;
    }
    public void set_imageNames(ArrayList<String> _imageNames) {
        this._imageNames = _imageNames;
    }
    //endregion

    //region Location
    private LatLon _location;
    public LatLon get_location() {
        return _location;
    }
    public LatLng get_location_LatLng() { return _location.get_LatLng(); }
    public void set_location(LatLon _location) {
        this._location = _location;
    }
    //endregion

    //region Address
    private Address _address;
    public Address get_address() {
        return _address;
    }
    public void set_address(Address _address) {
        this._address = _address;
    }
    //endregion

    //region Description
    private String _description;
    public String get_description() {
        return _description;
    }
    public void set_description(String _description) {
        this._description = _description;
    }
    //endregion

    //region Website
    private String _website;
    public String get_website(){
        return _website;
    }
    public void set_website(String website){
        _website = website;
    }
    //endregion

    //region Start Time
    private Date _startTime;
    public Date get_startTime() {
        return _startTime;
    }
    public void set_startTime(Date _startTime) {
        this._startTime = _startTime;
    }
    //endregion

    //region Owner
    private User _owner;
    public User get_owner() { return _owner; }
    public void set_owner(User owner) { _owner = owner; }
    //endregion

    //region Attendees
    private ArrayList<User> _attendees;
    public ArrayList<User> get_attendees(){ return _attendees; }
    public void set_attendees(ArrayList<User> attendees) { _attendees = attendees; }
    public int get_attendeesCount() { return _attendees.size(); }
    public boolean add_attendee(User u) {
        if (userExists(u))
            return false;
        else
            _attendees.add(u);
        return true;
    }
    public boolean has_attendee(User u){
        if(_attendees != null){
            return userExists(u);
        }

        return false;
    }
    private boolean userExists(User u){
        for (User user: _attendees){
            if (user.equals(u)){
                return true;
            }
        }
        return false;
    }
    //endregion

    //Event Category
    private EventCategory _category;
    public EventCategory get_category() { return _category; }
    public void set_category(EventCategory category) { _category = category; }
    public String get_category_name(){ return _category.getName(); }
    //endregion

    //region Phone Number
    private String _phone;
    public String get_phone(){
        return _phone;
    }
    public void set_phone(String _phone){
        this._phone = _phone;
    }
    //endregion

    @Override
    public int hashCode(){
        if(get_entityId() != null){
            return get_entityId().hashCode();
        }

        return super.hashCode();
    }
}
