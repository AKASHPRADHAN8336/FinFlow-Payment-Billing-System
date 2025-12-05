package com.project.BillingService.dto;

import java.math.BigDecimal;

public class BillRequest {
    private Long userId;

    private String billType;
    private BigDecimal amount;

    public BillRequest() {
    }

    public BillRequest(Long userId, String billType, BigDecimal amount) {
        this.userId = userId;
        this.billType = billType;
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
