package com.group2.whatsup.Entities;

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

    public static EventCategory[] getCategories(){
        return new EventCategory[] { Sports, Fitness, Scholastic, Volunteering };
    }

}
