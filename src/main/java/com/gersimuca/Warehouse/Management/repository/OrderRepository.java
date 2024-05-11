package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.enumeration.Status;
import com.gersimuca.Warehouse.Management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("UPDATE Order o SET o.status = :newStatus WHERE o.orderId = :orderId AND o.status = :currentStatus")
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("newStatus") String newStatus, @Param("currentStatus") String currentStatus);

    Optional<List<Order>> findByStatusOrderBySubmittedDateDesc(Status status);
}
