package com.jrp.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrp.inventoryservice.dto.InventoryResponse;
import com.jrp.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
//@Slf4j
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	@SneakyThrows
	public List<InventoryResponse> isInStock(List<String> skuCode) {

//		// Illustration for timeout feature
//		log.info("START: Checking if item is in stock");
//		Thread.sleep(10000);
//		log.info("END: Checking if item is in stock");

		return inventoryRepository.findBySkuCodeIn(skuCode).stream()
									.map( inventory -> 
										InventoryResponse.builder()
											.skuCode(inventory.getSkuCode())
											.isInStock(inventory.getQuantity() > 0)
											.build()
									).toList();
	}
}
