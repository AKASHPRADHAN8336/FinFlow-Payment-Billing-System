package com.project.PaymentService.dto;

import com.project.PaymentService.model.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class RazorpayPaymentResponse {


    private Long paymentId;
    private Long userId;
    private Long billId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime paymentTime;
    private String razorpayOrderId;



    public RazorpayPaymentResponse(Long paymentId, Long userId, Long billId, BigDecimal amount, PaymentStatus status, LocalDateTime paymentTime,String razorpayOrderId) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.billId = billId;
        this.amount = amount;
        this.status = status;
        this.paymentTime = paymentTime;
        this.razorpayOrderId=razorpayOrderId;
    }



    public RazorpayPaymentResponse() {
    }



    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
}
