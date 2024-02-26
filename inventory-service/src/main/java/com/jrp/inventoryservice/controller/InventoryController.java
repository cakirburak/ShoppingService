package com.jrp.inventoryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jrp.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@GetMapping("/{sku-code}")
	@ResponseStatus(HttpStatus.OK)
	public boolean isInStock(@PathVariable("sku-code") String skuCode) {
		return inventoryService.isInStock(skuCode);
	}
}
