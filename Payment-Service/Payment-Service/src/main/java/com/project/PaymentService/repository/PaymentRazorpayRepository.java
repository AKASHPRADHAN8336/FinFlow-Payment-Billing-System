package com.project.PaymentService.repository;

import com.project.PaymentService.model.PaymentRazorpay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRazorpayRepository extends JpaRepository<PaymentRazorpay , Long> {


    Optional<PaymentRazorpay> findByRazorpayOrderId(String orderId);



}
