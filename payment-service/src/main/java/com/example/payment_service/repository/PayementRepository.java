package com.example.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payment_service.entity.Payment;

public interface PayementRepository extends JpaRepository<Payment, Integer> {

	public Payment findByOrderId(int orderId);

}
