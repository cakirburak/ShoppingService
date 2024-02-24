package com.jrp.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jrp.orderservice.dto.OrderLineItemDto;
import com.jrp.orderservice.dto.OrderRequest;
import com.jrp.orderservice.model.Order;
import com.jrp.orderservice.model.OrderLineItem;
import com.jrp.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

//		List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDtoList()
//															.stream()
//															.map(orderLineItemDto -> mapToDto(orderLineItemDto))
//															.toList();
//		same implementation as below 

		List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
				.toList();

		order.setOrderLineItems(orderLineItems);

		orderRepository.save(order);
	}

	private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {
		OrderLineItem orderLineItem = new OrderLineItem();
		orderLineItem.setPrice(orderLineItemDto.getPrice());
		orderLineItem.setQuantity(orderLineItemDto.getQuantity());
		orderLineItem.setSkuCode(orderLineItemDto.getSkuCode());

		return orderLineItem;
	}
}
