package com.gersimuca.Warehouse.Management.model;

import com.gersimuca.Warehouse.Management.enumeration.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDate submittedDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate deadlineDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
