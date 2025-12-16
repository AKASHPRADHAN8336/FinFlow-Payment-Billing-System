package com.project.PaymentService.controller;

import com.project.PaymentService.dto.*;
import com.project.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    @PostMapping("/pay")
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest request) {

        PaymentResponse paymentResponse = paymentService.processPayment(request);
        return new ResponseEntity<>(paymentResponse , HttpStatus.OK);
    }


    @PostMapping("/rozarpay")
    public ResponseEntity<RazorpayPaymentResponse> initiatePayment(
            @RequestBody RazorpayPaymentRequest request) {


        RazorpayPaymentResponse paymentResponse = paymentService.initiatePayment(request);

        return new ResponseEntity<>(paymentResponse , HttpStatus.OK);
    }


    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmPayment(
            @RequestBody PaymentConfirmationRequest request) {

        paymentService.confirmPayment(request);
        return ResponseEntity.ok().build();
    }
}
