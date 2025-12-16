package com.project.PaymentService.service;

import com.project.PaymentService.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);

    RazorpayPaymentResponse initiatePayment(RazorpayPaymentRequest request);

    void confirmPayment(PaymentConfirmationRequest request);
}


