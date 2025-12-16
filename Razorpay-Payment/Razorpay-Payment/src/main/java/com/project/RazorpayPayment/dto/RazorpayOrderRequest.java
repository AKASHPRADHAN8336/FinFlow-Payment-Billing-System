package com.project.RazorpayPayment.dto;


import lombok.*;

import java.math.BigDecimal;


public class RazorpayOrderRequest {

    private Long userId;
    private Long billId;
    private BigDecimal amount;
    private String currency;

    public RazorpayOrderRequest() {
    }

    public RazorpayOrderRequest(Long userId, Long billId, BigDecimal amount, String currency) {
        this.userId = userId;
        this.billId = billId;
        this.amount = amount;
        this.currency = currency;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
