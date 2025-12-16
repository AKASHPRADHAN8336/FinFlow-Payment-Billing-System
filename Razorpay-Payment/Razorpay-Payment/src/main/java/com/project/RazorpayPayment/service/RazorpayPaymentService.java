package com.project.RazorpayPayment.service;

import com.project.RazorpayPayment.dto.RazorpayOrderRequest;
import com.project.RazorpayPayment.dto.RazorpayOrderResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface RazorpayPaymentService {

    RazorpayOrderResponse createRazorpayOrder(RazorpayOrderRequest request);

    void processWebhook(String payload, HttpServletRequest request);
}
