package com.project.PaymentService.dto;

import com.project.PaymentService.model.Payment;

public class PaymentRequestToPayment {


    public static Payment paymentToPaymentRes(PaymentRequest paymentRequest){
        Payment payment = new Payment(
                paymentRequest.getUserId(),
                paymentRequest.getBillId(),
                paymentRequest.getAmount()
        );


        return payment;
    }
}
