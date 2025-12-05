package com.project.PaymentService.dto;

import java.math.BigDecimal;

public class WalletResponse {


    private Long userId;
    private BigDecimal amount;

    public WalletResponse(Long userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public WalletResponse() {
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
