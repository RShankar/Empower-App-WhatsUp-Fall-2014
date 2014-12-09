package edu.fau.whatsup.ServiceContracts.Entities;

public interface IEntityService<T> {
    T GetById(String id);
    T Save(T arg);
    boolean Delete(T arg);
    void DeleteAll();
}
