package com.finki.MyTable.model;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String restaurantName;

    public String restaurantAddress;

    public String restaurantEmail;

    public String restaurantPassword;

    public boolean isActive;

    public String restaurantLogo;

    public Long billingAccountNumber;


    public boolean containsMenuItem(String itemName, List<MenuItem> menu)
    {
        for (MenuItem menuItem: menu) {
            if(menuItem.itemName.equals(itemName))
                return true;
        }
        return false;
    }
}
