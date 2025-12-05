package com.project.PaymentService.service.Impl;

import com.project.PaymentService.dto.*;
import com.project.PaymentService.model.Payment;
import com.project.PaymentService.model.PaymentStatus;
import com.project.PaymentService.repository.PaymentRepository;
import com.project.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private WebClient webClient;
    @Override
    public PaymentResponse processPayment(PaymentRequest request) {

//        UserResponse userResponse = webClient
//                .get()
//                .uri("http://localhost:8081/users/GetUser/" + request.getUserId())
//                .retrieve()
//                .bodyToMono(UserResponse.class)
//                .block();


        BillingResponse bill = webClient
                .get()
                .uri("http://localhost:8083/billing/" + request.getBillId())
                .retrieve()
                .bodyToMono(BillingResponse.class)
                .block();


//        BigDecimal balance = webClient
//                .get()
//                .uri("http://localhost:8082/wallet/balance/" + request.getUserId())
//                .retrieve()
//                .bodyToMono(BigDecimal.class)
//                .block();

        BigDecimal balance = bill.getAmount();
        if (balance.compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient Wallet Balance");
        }

        if (bill.getAmount().compareTo(request.getAmount()) != 0) {
            throw new RuntimeException("Payment amount must be equal to the Bill amount");
        }
        WalletResponse walletRequest = new WalletResponse(request.getUserId(), request.getAmount());

        webClient
                .post()
                .uri("http://localhost:8082/wallet/deduct")
                .bodyValue(walletRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();




       Boolean message =  webClient
                .put()
                .uri("http://localhost:8083/billing/updateStatus")
                .bodyValue(bill)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();


//       System.out.println(message);


        Payment payment = PaymentRequestToPayment.paymentToPaymentRes(request);
        if(message){
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            return PaymentToPaymentResponse.paymentToPaymentResponse(payment);
        }

            payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);
        return PaymentToPaymentResponse.paymentToPaymentResponse(payment);


    }
}