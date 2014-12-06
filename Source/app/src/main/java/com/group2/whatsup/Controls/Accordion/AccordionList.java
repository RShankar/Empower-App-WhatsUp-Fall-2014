package com.group2.whatsup.Controls.Accordion;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;

import com.group2.whatsup.Controls.BaseControl;
import com.group2.whatsup.Debug.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccordionList<T> extends BaseControl {
    private HashMap<String, AccordionListItem<T>> _itemMap;
    private ArrayList<AccordionListItem<T>> _itemsList;

    private IAccordionItemSelected<T> clickAction;
    private IAccordionItemView<T> itemView;
    private IAccordionGroupView<T> groupView;

    public AccordionList(){
        _itemMap = new HashMap<String, AccordionListItem<T>>();
        _itemsList = new ArrayList<AccordionListItem<T>>();

        //Example of how to call the event.
        //this.clickAction.ItemSelected(GetSection("asdf").GetItems().get(0).GetData());
    }

    public AccordionListItem<T> AddSection(String item){
        if(!_itemMap.containsKey(item)) {
            AccordionListItem<T> newItem = new AccordionListItem<T>();
            newItem.SetLabel(item);
            _itemMap.put(item, newItem);
            _itemsList.add(newItem);
            return newItem;
        }

        return _itemMap.get(item);
    }

    public void RemoveSection(String item){
        if(_itemMap.containsKey(item)){
            AccordionListItem<T> removedItem = _itemMap.remove(item);
            _itemsList.remove(removedItem);
        }
    }

    public ArrayList<AccordionListItem<T>> GetSections(){
        return _itemsList;
    }

    public AccordionListItem<T> GetSection(String item){
        if(_itemMap.containsKey(item)){
            return _itemMap.get(item);
        }

        return null;
    }

    public AccordionListItem<T> GetSection(int index){
        if(_itemsList.size() > index){
            return _itemsList.get(index);
        }

        return null;
    }

    public void CreateFromListAndSectionSpecification(List<T> items, String getterToGroupWith){

        if(items.size() > 0){
            T item = items.get(0);
            Method[] mtds = item.getClass().getMethods();

            //region Method Definitions
            for(Method m : mtds){
                String mName = m.getName();
                String returnType = m.getReturnType().getSimpleName();

                //region Method specified found.
                if(mName.equals(getterToGroupWith) && returnType.equals("String"))
                {
                    try{
                        for(T i : items){
                            String label = i.getClass().getMethod(mName).invoke(i).toString();
                            AddSection(label);
                            GetSection(label).AddItem(i);
                        }
                    }
                    catch(Exception ex){
                        Log.Error("Error invoking method directly from reflected object: {0}", ex.getMessage());
                    }

                    break;
                }
                //endregion


            }
            //endregion
        }
    }

    public void SetActionOnClick(IAccordionItemSelected<T> clickAction){
        this.clickAction = clickAction;
    }

    //region Item View Func
    public void SetViewToAppear(IAccordionItemView<T> view){
        itemView = view;
    }

    public IAccordionItemView<T> GetViewToAppear(){
        return itemView;
    }
    //endregion

    //region Group View Func
    public void SetGroupViewToAppear(IAccordionGroupView<T> view) {
        groupView = view;
    }

    public IAccordionGroupView<T> GetGroupViewToAppear(){
        return groupView;
    }
    //endregion

    public void InitializeExpandableListView(ExpandableListView lv){
        if(clickAction != null){
            lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    T item = GetSection(groupPosition).GetItem(childPosition);
                    T e_id = GetSection(groupPosition).GetItemId(childPosition);
                    // return the ID here
                    clickAction.ItemSelected(item, e_id);
                    return true;
                }
            });

            lv.setAdapter(GetAdapter(lv.getContext()));
        }
        else{
            Log.Error("clickAction is not set! Make sure to call SetActionOnClick with an action to take on each click!!!");
        }

    }

    public AccordionListAdapter GetAdapter(Context c){
        if(itemView != null && groupView != null){
            return new AccordionListAdapter(this, c);
        }
        else{
            Log.Error("No item view or group view specified! Please make sure to initialize these via SetViewToAppear and SetGroupViewToAppear");
        }

        return null;
    }


}
