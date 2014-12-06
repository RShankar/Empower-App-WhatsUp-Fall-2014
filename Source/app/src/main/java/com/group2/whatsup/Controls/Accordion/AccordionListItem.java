package com.group2.whatsup.Controls.Accordion;

import com.group2.whatsup.Controls.BaseControl;

import java.util.ArrayList;

public class AccordionListItem<T> extends BaseControl {
    private ArrayList<AccordionListItemChild<T>> items;
    private String label;

    public AccordionListItem(){
        items = new ArrayList<AccordionListItemChild<T>>();
    }

    public void AddItem(T item, T id){
        AccordionListItemChild<T> addContainer = new AccordionListItemChild<T>();
        addContainer.SetData(item, id);
        items.add(addContainer);
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

    // this might only be for displaying the stuff.
    public T GetItem(int index){
        if(items.size() > index){
            return items.get(index).GetData();
        }

        return null;
    }

    public T GetItemId(int index){
        if(items.size() > index){
            return items.get(index).GetId();
        }

        return null;
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
