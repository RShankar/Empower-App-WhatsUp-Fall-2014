package com.group2.whatsup.Controls.Accordion;

public class AccordionListItemChild<T> {
    T data;
    T id;

    public void SetData(T data){
        this.data = data;
    }

    public void SetData(T data, T id){
        this.data = data;
        this.id = id;
    }

    public T GetData(){
        return this.data;
    }
    public T GetId(){
        return this.id;
    }
}
