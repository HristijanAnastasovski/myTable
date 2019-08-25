package com.finki.MyTable.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="menu_item")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    public Long id;

    public String itemName;

    public String itemShortDescription;

    public double price;

    public int quantity;

    public String menuImage;

    @ManyToOne
    public Restaurant restaurant;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public List<OrderedMenuItem> orderedMenuItems = new ArrayList<>();



}
