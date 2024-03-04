package com.jrp.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.jrp.notificationservice.event.OrderPlacedEvent;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	
	@KafkaListener(topics="notificationTopic")
	public void consumeNotificationTopic(OrderPlacedEvent orderPlacedEvent) {
		log.info("Recieved Notification from Order Service - {}", orderPlacedEvent.getOrderNumber());
	}

}
