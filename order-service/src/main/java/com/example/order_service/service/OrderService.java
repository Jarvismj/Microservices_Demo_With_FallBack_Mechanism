package com.example.order_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.order_service.common.Payment;
import com.example.order_service.common.TransactionRequest;
import com.example.order_service.common.TransactionResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
@RefreshScope
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	private Logger log = LoggerFactory.getLogger(OrderService.class);
	
	
	
	@Value("${microservice.payment-service.endpoints.endpoint.uri}")
	private String ENDPOINT_URL;

	
	public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
		String resp = " ";
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());
		log.info("OrderService request : {}",new ObjectMapper().writeValueAsString(request));
		Payment response = restTemplate.postForObject(ENDPOINT_URL, payment,
				Payment.class);
		log.info("Payment-service response from OrderService Rest call : {}",new ObjectMapper().writeValueAsString(response));
		resp = response.getPaymentStatus().equals("success") ? "payment processing successful and order placed"
				: "Payment failed and order is added at the cart.";
		repository.save(order);

		return new TransactionResponse(order, response.getAmount(), response.getTransactionId(), resp);
	}
	
	

}
