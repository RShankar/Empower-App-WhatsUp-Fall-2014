package edu.fau.whatsup.Controls.Accordion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class AccordionListAdapter<T> extends BaseExpandableListAdapter {
    private Context _context;
    private AccordionList<T> _parent;
    private IAccordionItemView<T> _viewMethod;

    public AccordionListAdapter(AccordionList<T> parent, Context context) {
        _parent = parent;
        _context = context;


    }

    public Object getChild(int groupPosition, int childPosition) {
        return _parent.GetSection(groupPosition).GetItem(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        T item = (T)getChild(groupPosition, childPosition);
        return _parent.GetViewToAppear().viewToDisplay(item, convertView);
    }

    public int getChildrenCount(int groupPosition) {
        return _parent.GetSection(groupPosition).GetItems().size();
    }

    public Object getGroup(int groupPosition) {
        return _parent.GetSection(groupPosition);
    }

    public int getGroupCount() {
        return _parent.GetSections().size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        AccordionListItem<T> item = (AccordionListItem<T>)getGroup(groupPosition);
        return _parent.GetGroupViewToAppear().viewToAppear(item, convertView);
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
