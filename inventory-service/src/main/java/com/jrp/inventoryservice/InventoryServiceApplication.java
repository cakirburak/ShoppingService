package com.jrp.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jrp.inventoryservice.model.Inventory;
import com.jrp.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadInventoryData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			// sku code for iphone 13
			inventory.setSkuCode("123456");
			inventory.setQuantity(100);

			// sku code for iphone 14
			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("654321");
			inventory2.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory2);
		};
	}

}
