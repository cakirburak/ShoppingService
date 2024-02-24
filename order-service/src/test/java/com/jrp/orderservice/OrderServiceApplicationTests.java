package com.jrp.orderservice;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrp.orderservice.dto.OrderLineItemDto;
import com.jrp.orderservice.dto.OrderRequest;
import com.jrp.orderservice.repository.OrderRepository;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

	static MySQLContainer mysqlContainer = new MySQLContainer("mysql:8.3.0");

	static {
		mysqlContainer.start();
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private OrderRepository orderRepository;

	@DynamicPropertySource
	static void configureTestProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.driver-class-name", () -> mysqlContainer.getDriverClassName());
		registry.add("spring.datasource.url", () -> mysqlContainer.getJdbcUrl());
		registry.add("spring.datasource.username", () -> mysqlContainer.getUsername());
		registry.add("spring.datasource.password", () -> mysqlContainer.getPassword());
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");

	}

	@Test
	void shouldCreateOrder() throws Exception {

		OrderRequest orderRequest = getOrderRequest();
		String orderRequestString = objectMapper.writeValueAsString(orderRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order").contentType(MediaType.APPLICATION_JSON)
				.content(orderRequestString)).andExpect(status().isCreated());

		Assertions.assertTrue(orderRepository.findAll().size() == 1);
	}

	private OrderRequest getOrderRequest() {

		List<OrderLineItemDto> orderLineItemsDtoList = new ArrayList<OrderLineItemDto>();
		OrderLineItemDto orderLineItemDto = OrderLineItemDto.builder().skuCode("123456").price(new BigDecimal(28.99))
				.quantity(2).build();
		orderLineItemsDtoList.add(orderLineItemDto);

		OrderRequest orderRequest = OrderRequest.builder().orderLineItemsDtoList(orderLineItemsDtoList).build();
		return orderRequest;
	}

}
