package com.project.RazorpayPayment.service.Impl;



import com.project.RazorpayPayment.dto.PaymentConfirmationRequest;
import com.project.RazorpayPayment.dto.RazorpayOrderRequest;
import com.project.RazorpayPayment.dto.RazorpayOrderResponse;
import com.project.RazorpayPayment.model.PaymentTransaction;
import com.project.RazorpayPayment.repository.PaymentTransactionRepository;
import com.project.RazorpayPayment.service.RazorpayPaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import jakarta.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RazorpayPaymentServiceImpl implements RazorpayPaymentService {

    @Autowired
    private WebClient webClient;




    private final PaymentTransactionRepository paymentTransactionRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    public RazorpayPaymentServiceImpl(PaymentTransactionRepository paymentTransactionRepository) {
        this.paymentTransactionRepository = paymentTransactionRepository;
    }

    @Override
    public RazorpayOrderResponse createRazorpayOrder(RazorpayOrderRequest request) {
        try {
            RazorpayClient client =
                    new RazorpayClient(keyId, keySecret);

            long amountInPaise =
                    request.getAmount()
                            .multiply(BigDecimal.valueOf(100))
                            .longValueExact();

            JSONObject orderReq = new JSONObject();
            orderReq.put("amount", amountInPaise);
            orderReq.put("currency", "INR");
            orderReq.put("receipt", String.valueOf(request.getBillId()));
//            orderReq.put("receipt", request.getBillId());

            Order order = client.orders.create(orderReq);

//            paymentTransactionRepository.save(PaymentTransaction.builder()
//                    .userId(request.getUserId())
//                    .billId(request.getBillId())
//                    .razorpayOrderId(order.get("id"))
//                    .amount(request.getAmount())
//                    .status("CREATED")
//                    .createdAt(LocalDateTime.now())
//                    .build());


            PaymentTransaction txn = new PaymentTransaction();
            txn.setUserId(request.getUserId());
            txn.setBillId(request.getBillId());
            txn.setRazorpayOrderId(order.get("id"));
            txn.setAmount(request.getAmount());
            txn.setStatus("CREATED");
            txn.setCreatedAt(LocalDateTime.now());

            paymentTransactionRepository.save(txn);


            RazorpayOrderResponse razorpayOrderResponse = new RazorpayOrderResponse();

            razorpayOrderResponse.setRazorpayOrderId(order.get("id"));
            razorpayOrderResponse.setRazorpayKey(keyId);
            razorpayOrderResponse.setAmount(request.getAmount());
            razorpayOrderResponse.setCurrency("INR");



            return razorpayOrderResponse;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processWebhook(String payload, HttpServletRequest request) {

        try {
            String signature = request.getHeader("X-Razorpay-Signature");

            Utils.verifyWebhookSignature(payload, signature, webhookSecret);

            JSONObject json = new JSONObject(payload);

            String event = json.getString("event");
            JSONObject entity =
                    json.getJSONObject("payload")
                            .getJSONObject("payment")
                            .getJSONObject("entity");

            String orderId = entity.getString("order_id");

            PaymentTransaction txn =
                    paymentTransactionRepository.findByRazorpayOrderId(orderId).orElseThrow();

            boolean success = event.equals("payment.captured");

            txn.setStatus(success ? "PAID" : "FAILED");
            txn.setRazorpayPaymentId(entity.getString("id"));
            txn.setUpdatedAt(LocalDateTime.now());
            paymentTransactionRepository.save(txn);


            PaymentConfirmationRequest paymentConfirmationRequest=new PaymentConfirmationRequest(
                    orderId,entity.getString("id"),success
            );

            // Notify Payment Service
            webClient.post()
                    .uri("http://localhost:8080/payments/confirm")
                    .bodyValue(paymentConfirmationRequest)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
