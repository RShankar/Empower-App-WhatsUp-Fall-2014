package com.group2.whatsup.Controls;

import android.view.View;

public interface IAccordionItemView<T> {
    View viewToDisplay(T item);
}
