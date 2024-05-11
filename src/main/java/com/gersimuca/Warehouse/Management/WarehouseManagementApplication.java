package com.gersimuca.Warehouse.Management;

import com.gersimuca.Warehouse.Management.dto.request.RegisterRequest;
import com.gersimuca.Warehouse.Management.model.Item;
import com.gersimuca.Warehouse.Management.repository.ItemRepository;
import com.gersimuca.Warehouse.Management.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static com.gersimuca.Warehouse.Management.enumeration.Role.SYSTEM_ADMIN;
import static com.gersimuca.Warehouse.Management.enumeration.Role.WAREHOUSE_MANAGER;

@SpringBootApplication
public class WarehouseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService authenticationService, ItemRepository itemRepository
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.username("admin")
					.password("admin")
					.role(SYSTEM_ADMIN)
					.build();
			System.out.println("Admin token: " + authenticationService.register(admin));

			var manager = RegisterRequest.builder()
					.username("manager")
					.password("123")
					.role(WAREHOUSE_MANAGER)
					.build();
			System.out.println("Manager token: " + authenticationService.register(manager));

			itemRepository.saveAll(List.of(
					Item.builder().itemName("Laptop").quantity(5).unitPrice(1200.00).build(),
					Item.builder().itemName("Mouse").quantity(10).unitPrice(20.50).build(),
					Item.builder().itemName("Keyboard").quantity(8).unitPrice(40.00).build(),
					Item.builder().itemName("Monitor").quantity(3).unitPrice(300.00).build()
			));
		};
	}

}
