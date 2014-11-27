package com.group2.whatsup.Controls;

import java.util.ArrayList;
import java.util.HashMap;

public class AccordionListItem<T> {
    private ArrayList<AccordionListItemChild<T>> items;
    private String label;

    public AccordionListItem(){
        items = new ArrayList<AccordionListItemChild<T>>();
    }

    public void AddItem(T item){
        AccordionListItemChild<T> addContainer = new AccordionListItemChild<T>();
        addContainer.SetData(item);
        items.add(addContainer);
    }

    public void RemoveItem(T item){
        for(AccordionListItemChild<T> t : items){
            if(t.GetData().equals(t)){
                items.remove(item);
            }
        }
    }

    public void SetLabel(String label){
        this.label = label;
    }

    public String GetLabel(){
        return this.label;
    }

    public ArrayList<AccordionListItemChild<T>> GetItems(){
        return items;
    }
}
