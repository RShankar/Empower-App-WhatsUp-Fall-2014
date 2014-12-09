package edu.fau.whatsup.Controls.Accordion;

import android.view.View;

public interface IAccordionGroupView<T> {
    View viewToAppear(AccordionListItem<T> item, View convertView);
}
