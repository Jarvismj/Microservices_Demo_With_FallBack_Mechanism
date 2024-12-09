package com.example.cloud_gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
	
	@RequestMapping("/orderFallBack")
	public Mono<String> orderServiceFallBack()
	{
		return Mono.just("Order Service is taking too long to respond or down. Please try again after sometime.");
	}
	
	@RequestMapping("/paymentFallBack")
	public Mono<String> paymentServiceFallBack()
	{
		return Mono.just("Payment Service is taking too long to respond or down. Please try again after sometime.");
	}

}
