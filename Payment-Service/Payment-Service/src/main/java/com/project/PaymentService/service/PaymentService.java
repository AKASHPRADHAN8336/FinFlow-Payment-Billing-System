package com.project.PaymentService.service;

import com.project.PaymentService.dto.PaymentRequest;
import com.project.PaymentService.dto.PaymentResponse;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);
}
