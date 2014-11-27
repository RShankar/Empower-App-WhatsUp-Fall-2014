package com.group2.whatsup.Controls;

public class AccordionListItemChild<T> {
    T data;

    public void SetData(T data){
        this.data = data;
    }

    public T GetData(){
        return this.data;
    }
}
