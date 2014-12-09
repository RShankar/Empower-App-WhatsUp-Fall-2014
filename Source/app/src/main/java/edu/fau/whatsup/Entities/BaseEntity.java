package edu.fau.whatsup.Entities;

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

    //region EntityID
    private String _entityId;
    public String get_entityId() { return _entityId; }
    public void set_entityId(String entityId) { _entityId = entityId; }
    //endregion

    @Override
    public boolean equals(Object o){
        if(o instanceof BaseEntity){
            BaseEntity other = (BaseEntity) o;
            return other.get_entityId().equals(get_entityId());
        }

        return super.equals(o);
    }
}
