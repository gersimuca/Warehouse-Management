package com.gersimuca.Warehouse.Management.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String itemName;

    private int quantity;

    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}