package com.gersimuca.Warehouse.Management;

import com.gersimuca.Warehouse.Management.dto.RegisterRequest;
import com.gersimuca.Warehouse.Management.model.User;
import com.gersimuca.Warehouse.Management.repository.UserRepository;
import com.gersimuca.Warehouse.Management.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.gersimuca.Warehouse.Management.enumeration.Role.SYSTEM_ADMIN;
import static com.gersimuca.Warehouse.Management.enumeration.Role.WAREHOUSE_MANAGER;

@SpringBootApplication
public class WarehouseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService authenticationService, UserRepository userRepository
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

		};
	}

}
