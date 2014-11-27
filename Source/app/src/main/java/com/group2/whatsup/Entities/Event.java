package com.group2.whatsup.Entities;

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

    //region End Time
    private Date _endTime;
    public Date get_endTime() {
        return _endTime;
    }

    public void set_endTime(Date _endTime) {
        this._endTime = _endTime;
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
    //endregion

    //Event Category
    private EventCategory _category;
    public EventCategory get_category() { return _category; }
    public void set_category(EventCategory category) { _category = category; }
    public String get_category_name(){ return _category.getName(); }
    //endregion
}
