package com.group2.whatsup;

import com.group2.whatsup.Controls.Accordion.AccordionList;
import com.group2.whatsup.Controls.Accordion.AccordionListItem;
import com.group2.whatsup.Controls.Accordion.IAccordionGroupView;
import com.group2.whatsup.Controls.Accordion.IAccordionItemSelected;
import com.group2.whatsup.Controls.Accordion.IAccordionItemView;
import com.group2.whatsup.Interop.WUBaseActivity;
import com.group2.whatsup.Managers.ToastManager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.content.Intent;
import android.view.MenuItem;




public class EventList extends WUBaseActivity {
    private ExpandableListView expListView;
    private AccordionList<String> stringExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_event_list);
    }

    @Override
    protected void initializeViewControls() {
        super.initializeViewControls();
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        stringExample = new AccordionList<String>();
    }

    @Override
    protected void setViewTheme() {
        super.setViewTheme();

        AccordionListItem<String> comm = stringExample.AddSection("Community");
        AccordionListItem<String> rec = stringExample.AddSection("Recreation");
        AccordionListItem<String> sch = stringExample.AddSection("School");

        comm.AddItem("Test1a");
        comm.AddItem("Test1b");

        rec.AddItem("Test2a");
        rec.AddItem("Test2b");

        sch.AddItem("Test3a");
        sch.AddItem("Test3b");

        //region Action On Click
        stringExample.SetActionOnClick(new IAccordionItemSelected<String>() {
            @Override
            public void ItemSelected(String selectedItem) {
                ToastManager.Instance().SendMessage(selectedItem, true);
            }
        });
        //endregion

        //region Group View
        stringExample.SetGroupViewToAppear(new IAccordionGroupView<String>() {
            @Override
            public View viewToAppear(AccordionListItem<String> item, View convertView) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.event_group, null);
                }
                TextView txt = (TextView) convertView.findViewById(R.id.group);
                txt.setTypeface(null, Typeface.BOLD);
                txt.setText(item.GetLabel());
                return convertView;
            }
        });
        //endregion

        //region Item View
        stringExample.SetViewToAppear(new IAccordionItemView<String>() {
            @Override
            public View viewToDisplay(String item, View convertView) {

                LayoutInflater inflater = getLayoutInflater();

                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.event_child, null);
                }

                TextView txt = (TextView) convertView.findViewById(R.id.child);
                txt.setText(item);

                return convertView;
            }
        });
        //endregion

        stringExample.InitializeExpandableListView(expListView);
    }



    public void changeIntent(){
        Intent intent = new Intent(this, EventDetails.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();        switch (item.getItemId()) {
            case R.id.map:
                // open activity
                Intent intent = new Intent(this, MapScreen.class);
                startActivity(intent);
                return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
