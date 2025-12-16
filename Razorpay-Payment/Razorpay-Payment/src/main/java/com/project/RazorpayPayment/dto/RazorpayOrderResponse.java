package com.project.RazorpayPayment.dto;
import lombok.*;

import java.math.BigDecimal;


public class RazorpayOrderResponse {

    private String razorpayOrderId;
    private BigDecimal amount;
    private String currency;
    private String razorpayKey;
    private String status;

    public RazorpayOrderResponse() {
    }

    public RazorpayOrderResponse(String razorpayOrderId, BigDecimal amount, String currency, String razorpayKey, String status) {
        this.razorpayOrderId = razorpayOrderId;
        this.amount = amount;
        this.currency = currency;
        this.razorpayKey = razorpayKey;
        this.status = status;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
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

    public String getRazorpayKey() {
        return razorpayKey;
    }

    public void setRazorpayKey(String razorpayKey) {
        this.razorpayKey = razorpayKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
