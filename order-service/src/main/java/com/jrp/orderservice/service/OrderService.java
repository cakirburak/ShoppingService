package com.jrp.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.jrp.orderservice.dto.InventoryResponse;
import com.jrp.orderservice.dto.OrderLineItemDto;
import com.jrp.orderservice.dto.OrderRequest;
import com.jrp.orderservice.model.Order;
import com.jrp.orderservice.model.OrderLineItem;
import com.jrp.orderservice.repository.OrderRepository;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	
	private final WebClient.Builder webClientBuilder;
	
	private final Tracer tracer;

	public String placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDtoList()
														.stream().map(this::mapToDto)
														.toList();

		order.setOrderLineItems(orderLineItems);

		// Get all the sku codes from db
		List<String> skuCodes = order.getOrderLineItems().stream()
								.map(OrderLineItem::getSkuCode)
								.toList();

		Span inventoryServiceLookup = tracer.nextSpan().name("inventoryServiceLookup");
		tracer.withSpan(inventoryServiceLookup.start());

		try(Tracer.SpanInScope soanInScope = tracer.withSpan(inventoryServiceLookup.start())){
			// Get all the sku codes from request
			InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
							.uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
							.retrieve()
							.bodyToMono(InventoryResponse[].class)
							.block();

			// check items are in stock
			boolean allProductsInStock = Arrays.stream(inventoryResponsArray).allMatch(InventoryResponse::isInStock);
			
			if(allProductsInStock) {			
				orderRepository.save(order);
				return "Order placed successfuly!";
			} else {
				throw new IllegalArgumentException("Product is not in stock!");
			}
		} finally {
			inventoryServiceLookup.end();
		}

	}

	private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {
		OrderLineItem orderLineItem = new OrderLineItem();
		orderLineItem.setPrice(orderLineItemDto.getPrice());
		orderLineItem.setQuantity(orderLineItemDto.getQuantity());
		orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());

		return orderLineItem;
	}
}
