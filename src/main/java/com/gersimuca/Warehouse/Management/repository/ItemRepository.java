package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i.quantity FROM Item i WHERE i.itemId = :itemId")
    Integer findQuantityById(@Param("itemId") Long itemId);

    @Query("SELECT i FROM Item i WHERE i.itemId IN :itemIds")
    List<Item> findItemsByIds(@Param("itemIds") List<Long> itemIds);
}
