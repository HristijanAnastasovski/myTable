package com.finki.MyTable.model.factory;

import com.finki.MyTable.model.CustomerOrder;
import com.finki.MyTable.model.MenuItem;
import com.finki.MyTable.model.OrderedMenuItem;

public class OrderedMenuItemFactory {
    public static OrderedMenuItem create(CustomerOrder order, MenuItem menuItem, int quantity){
        OrderedMenuItem orderedMenuItem = new OrderedMenuItem();
        orderedMenuItem.menuItem = menuItem;
        orderedMenuItem.customerOrder=order;
        orderedMenuItem.quantity = quantity;

        return orderedMenuItem;
    }
}
