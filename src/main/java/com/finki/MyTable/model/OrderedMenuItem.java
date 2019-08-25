package com.finki.MyTable.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "ordered_menu_item")
public class OrderedMenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ordered_menu_item_id")
    public Long id;

    @ManyToOne
    @JsonIgnoreProperties("orderedMenuItems")
    public MenuItem menuItem;

    @ManyToOne
    @JsonIgnoreProperties("orderedMenuItems")
    public CustomerOrder customerOrder;

    public int quantity;

    void editQuantity(int quantity)
    {
        this.quantity += quantity;
    }
}
