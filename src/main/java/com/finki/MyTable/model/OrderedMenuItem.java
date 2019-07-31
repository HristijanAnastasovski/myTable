package com.finki.MyTable.model;

import javax.persistence.*;

@Entity
@Table(name = "ordered_menu_item")
public class OrderedMenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ordered_menu_item_id")
    public Long id;

    @ManyToOne
    public MenuItem menuItem;

    @ManyToOne
    public CustomerOrder customerOrder;

    public int quantity;

    void editQuantity(int quantity)
    {
        this.quantity += quantity;
    }
}
