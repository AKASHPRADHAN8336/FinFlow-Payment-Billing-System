package com.project.PaymentService.service.Impl;

import com.project.PaymentService.dto.*;
import com.project.PaymentService.model.Payment;
import com.project.PaymentService.model.PaymentRazorpay;
import com.project.PaymentService.model.PaymentStatus;
import com.project.PaymentService.repository.PaymentRazorpayRepository;
import com.project.PaymentService.repository.PaymentRepository;
import com.project.PaymentService.service.PaymentService;
import com.project.RazorpayPayment.dto.RazorpayOrderResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentRazorpayRepository paymentRazorpayRepository;
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

    @Override
    @Transactional
    public RazorpayPaymentResponse initiatePayment(RazorpayPaymentRequest request) {

        PaymentRazorpay payment = new PaymentRazorpay();

        payment.setUserId(request.getUserId());
        payment.setBillId(request.getBillId());
        payment.setAmount(request.getAmount());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());


//        PaymentRazorpay payment = PaymentRazorpay.builder()
//                .userId(request.getUserId())
//                .billId(request.getBillId())
//                .amount(request.getAmount())
//                .status(PaymentStatus.PENDING)
//                .createdAt(LocalDateTime.now())
//                .build();

        paymentRazorpayRepository.save(payment);

        RazorpayOrderResponse order =
                webClient.post()
                        .uri("http://localhost:8087/razorpay/create-order")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(RazorpayOrderResponse.class)
                        .block();

        payment.setRazorpayOrderId(order.getRazorpayOrderId());
        paymentRazorpayRepository.save(payment);

        RazorpayPaymentResponse paymentResponse=new RazorpayPaymentResponse();

        paymentResponse.setPaymentId(payment.getId());
        paymentResponse.setUserId(payment.getUserId());
        paymentResponse.setBillId(paymentResponse.getBillId());
        paymentResponse.setAmount(payment.getAmount());
        paymentResponse.setStatus(payment.getStatus());
        paymentResponse.setPaymentTime(payment.getUpdatedAt());
        paymentResponse.setRazorpayOrderId(paymentResponse.getRazorpayOrderId());

        return paymentResponse;

    }

    @Override
    public void confirmPayment(PaymentConfirmationRequest request) {


        PaymentRazorpay payment = paymentRazorpayRepository
                .findByRazorpayOrderId(request.getRazorpayOrderId())
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));

        boolean message = false;
        if (request.isSuccess()) {

            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setRazorpayPaymentId(request.getRazorpayPaymentId());


            Long billId = payment.getBillId();
            Long userId = payment.getUserId();
            BigDecimal amt = payment.getAmount();

            WalletResponse walletRequest = new WalletResponse(userId, amt);

            BillingResponse bill = webClient
                    .get()
                    .uri("http://localhost:8083/billing/" + billId)
                    .retrieve()
                    .bodyToMono(BillingResponse.class)
                    .block();

            // deduct wallet
            webClient.post()
                    .uri("http://localhost:8082/wallet/deduct")
                    .bodyValue(walletRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();


            // update bill
            message = webClient
                    .put()
                    .uri("http://localhost:8083/billing/updateStatus")
                    .bodyValue(bill)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        if (message) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setUpdatedAt(LocalDateTime.now());
            paymentRazorpayRepository.save(payment);


        }
        payment.setUpdatedAt(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PENDING);
        paymentRazorpayRepository.save(payment);


//
//
//        payment.setUpdatedAt(LocalDateTime.now());
//        paymentRazorpayRepository.save(payment);

    }
}