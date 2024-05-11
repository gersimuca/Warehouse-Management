package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.model.Order;
import com.gersimuca.Warehouse.Management.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Modifying
    @Query("DELETE FROM OrderItem oi WHERE oi.order.orderId = :orderId")
    void deleteByOrderId(@Param("orderId") Long orderId);

    void deleteOrderItemsByOrder(Order order);
}
