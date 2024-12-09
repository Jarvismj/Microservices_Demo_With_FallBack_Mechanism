package com.example.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order_service.common.Payment;
import com.example.order_service.common.TransactionRequest;
import com.example.order_service.common.TransactionResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService service;

	public static final String PAYMENT_SERVICE = "payment-service";

	@PostMapping("/completeOrder")
	@CircuitBreaker(name = PAYMENT_SERVICE, fallbackMethod = "getAllAvailablePaymentData")
	public TransactionResponse bookOrder(@RequestBody TransactionRequest request) throws JsonProcessingException {

		return service.saveOrder(request);
	}

	public TransactionResponse getAllAvailablePaymentData(Exception e) {

		Order order = new Order(109, "Mobile", 1, 50000);
		return new TransactionResponse(order, 50000, "1000-sampletxid-xyz", "Circuit-Breaker Test Response");

	}
}
