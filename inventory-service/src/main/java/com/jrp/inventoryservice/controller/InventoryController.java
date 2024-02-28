package com.jrp.inventoryservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jrp.inventoryservice.dto.InventoryResponse;
import com.jrp.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	
	// http://localhost:8020/api/inventory?skuCode=123456&skuCode=654321
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
		return inventoryService.isInStock(skuCode);
	}
}
