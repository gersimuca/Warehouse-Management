package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
