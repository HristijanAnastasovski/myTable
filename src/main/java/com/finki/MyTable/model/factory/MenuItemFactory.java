package com.finki.MyTable.model.factory;

import com.finki.MyTable.model.MenuItem;
import com.finki.MyTable.model.Restaurant;

public class MenuItemFactory {
    public static MenuItem create(String itemName, String description, double price, int quantity, Restaurant restaurant, String menuImage){
        MenuItem menuItem = new MenuItem();
        menuItem.itemName=itemName;
        menuItem.itemShortDescription=description;
        menuItem.price=price;
        menuItem.quantity = quantity;
        menuItem.restaurant=restaurant;
        menuItem.menuImage = menuImage;
        return menuItem;

    }
}
