package edu.fau.whatsup.Controls.Accordion;

import android.view.View;

public interface IAccordionItemView<T> {
    View viewToDisplay(T item, View convertView);
}
