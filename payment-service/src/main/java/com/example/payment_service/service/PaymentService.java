package com.example.payment_service.service;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.payment_service.entity.Payment;
import com.example.payment_service.repository.PayementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class PaymentService {

	@Autowired
	private PayementRepository repository;
	
	
	private Logger log = LoggerFactory.getLogger(PaymentService.class);
public static final String PAYMENT_SERVICE="payment-service";
	
	
	public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setPaymentStatus(paymentProcessing());
		payment.setTransactionId(UUID.randomUUID().toString());
		log.info("PaymentService request : {}",new ObjectMapper().writeValueAsString(payment));
		return repository.save(payment);
	}
	
	

	public String paymentProcessing() {
		return new Random().nextBoolean() ? "success" : "false";
	}

	public Payment findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
		Payment payment = repository.findByOrderId(orderId);
		log.info("PaymentService findPaymentHistoryByOrderId : {}",new ObjectMapper().writeValueAsString(payment));
		return payment;
	}

}
