package com.group2.whatsup.Entities;

import java.util.ArrayList;
import java.util.List;

public enum EventCategory {

    Sports(0, "Sports"),
    Fitness(1, "Fitness"),
    Scholastic(2, "Scholastic"),
    Volunteering(3, "Volunteering");


    private String _name;
    private int _id;
    EventCategory(int id, String name){
        _id = id;
        _name = name;
    }

    public int getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public static List<String> getCategoryNames(){
        ArrayList<String> retVal = new ArrayList<String>();

        for(EventCategory cat : getCategories()){
            retVal.add(cat.getName());
        }

        return retVal;
    }

    public static EventCategory fromName(String name){
        EventCategory[] cats = getCategories();
        for(EventCategory cat : cats){
            if(cat.getName().equals(name)){
                return cat;
            }
        }

        //This is our default for the time being.
        return Sports;
    }

    public static EventCategory[] getCategories(){
        return new EventCategory[] { Sports, Fitness, Scholastic, Volunteering };
    }

}
