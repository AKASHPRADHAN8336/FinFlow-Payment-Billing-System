package com.project.PaymentService.dto;

import com.project.PaymentService.model.Payment;

public class PaymentToPaymentResponse {


    public static PaymentResponse paymentToPaymentResponse(Payment payment){

        PaymentResponse paymentResponse = new PaymentResponse(


                payment.getId(),
                payment.getUserId(),
                payment.getBillId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getPaymentTime()
        );


        return paymentResponse;



    }
}
