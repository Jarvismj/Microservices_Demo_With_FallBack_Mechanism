package com.example.payment_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment_service.entity.Payment;
import com.example.payment_service.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService service;
	
	public static final String PAYMENT_SERVICE="payment-service";
	
	@PostMapping("/completePayment")
	public Payment doPayment(@RequestBody Payment payment) throws JsonProcessingException
	{
		return service.doPayment(payment);
	}
	
	@GetMapping("/{orderId}")
	public Payment findPaymentHistoryByOrderId(@PathVariable int orderId) throws JsonProcessingException
	{
		return service.findPaymentHistoryByOrderId(orderId);
	}
	
	
	public Payment getAllAvailablePaymentData(Exception e)
	{
		return new Payment(1110,"success","1000-sampletxid-xyz",106,3450.00);
		
	}
}
