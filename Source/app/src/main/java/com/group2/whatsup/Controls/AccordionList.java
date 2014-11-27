package com.group2.whatsup.Controls;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.group2.whatsup.Debug.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccordionList<T> {
    private HashMap<String, AccordionListItem<T>> _itemMap;
    private ArrayList<AccordionListItem<T>> _itemsList;

    private IAccordionItemSelected<T> clickAction;

    public AccordionList(){
        _itemMap = new HashMap<String, AccordionListItem<T>>();
        _itemsList = new ArrayList<AccordionListItem<T>>();

        //Example of how to call the event.
        //this.clickAction.ItemSelected(GetSection("asdf").GetItems().get(0).GetData());
    }

    public void AddSection(String item){
        if(!_itemMap.containsKey(item)){
            AccordionListItem<T> newItem = new AccordionListItem<T>();
            newItem.SetLabel(item);
            _itemMap.put(item, newItem);
            _itemsList.add(newItem);
        }
    }

    public void RemoveSection(String item){
        if(_itemMap.containsKey(item)){
            AccordionListItem<T> removedItem = _itemMap.remove(item);
            _itemsList.remove(removedItem);
        }
    }

    public AccordionListItem<T> GetSection(String item){
        if(_itemMap.containsKey(item)){
            return _itemMap.get(item);
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

    public void InitializeExpandableListView(ExpandableListView lv){
        ExpandableListAdapter ela = new ExpandableListAdapter(){

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getGroupCount() {
                return 0;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return 0;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return null;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return null;
            }

            @Override
            public long getGroupId(int groupPosition) {
                return 0;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return false;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public void onGroupExpanded(int groupPosition) {

            }

            @Override
            public void onGroupCollapsed(int groupPosition) {

            }

            @Override
            public long getCombinedChildId(long groupId, long childId) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(long groupId) {
                return 0;
            }
        };
    }
}
