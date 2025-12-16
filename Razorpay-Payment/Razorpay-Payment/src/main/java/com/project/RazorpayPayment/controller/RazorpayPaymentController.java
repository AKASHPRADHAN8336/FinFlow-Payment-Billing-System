package com.project.RazorpayPayment.controller;

import com.project.RazorpayPayment.dto.RazorpayOrderRequest;
import com.project.RazorpayPayment.dto.RazorpayOrderResponse;
import com.project.RazorpayPayment.service.RazorpayPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/razorpay")
public class RazorpayPaymentController {

    @Autowired
    private final RazorpayPaymentService razorpayPaymentService;

    public RazorpayPaymentController(RazorpayPaymentService razorpayPaymentService) {
        this.razorpayPaymentService = razorpayPaymentService;
    }


    @PostMapping("/create-order")
    public ResponseEntity<RazorpayOrderResponse> createOrder(
            @RequestBody RazorpayOrderRequest request) {

        RazorpayOrderResponse response =
                razorpayPaymentService.createRazorpayOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            HttpServletRequest request) {

        razorpayPaymentService.processWebhook(payload, request);
        return ResponseEntity.ok("Webhook processed");
    }
}
