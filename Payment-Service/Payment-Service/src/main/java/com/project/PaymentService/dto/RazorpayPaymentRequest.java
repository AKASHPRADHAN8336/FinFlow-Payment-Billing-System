package com.project.PaymentService.dto;

import lombok.Builder;

import java.math.BigDecimal;

public class    RazorpayPaymentRequest {


    private Long userId;
    private Long billId;
    private BigDecimal amount;

    public RazorpayPaymentRequest(Long userId, Long billId, BigDecimal amount) {
        this.userId = userId;
        this.billId = billId;
        this.amount = amount;
    }

    public RazorpayPaymentRequest() {
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
}
