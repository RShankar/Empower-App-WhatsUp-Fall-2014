package com.group2.whatsup.Entities;

public abstract class BaseEntity {

    public BaseEntity(String entityName){
        _entityName = entityName;
    }

    //region Entity Name
    public String getEntityName(){
        return _entityName;
    }
    private String _entityName;
    //endregion

    //region ID
    private int _id;
    public int get_id(){
        return _id;
    }
    public void set_id(int id){
        _id = id;
    }
    //endregion
}
