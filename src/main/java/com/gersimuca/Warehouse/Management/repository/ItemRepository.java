package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
}
