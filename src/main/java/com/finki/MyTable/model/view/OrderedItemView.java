package com.finki.MyTable.model.view;

import com.finki.MyTable.model.OrderedMenuItem;

public class OrderedItemView {
    public String itemName;
    public String itemShortDecription;
    public double price;
    public int quantity;

    public OrderedItemView(OrderedMenuItem orderedMenuItem){
        itemName = orderedMenuItem.menuItem.itemName;
        itemShortDecription = orderedMenuItem.menuItem.itemShortDescription;
        price = orderedMenuItem.menuItem.price;
        quantity = orderedMenuItem.quantity;

    }
}
