package com.project.WalletService.dto;

import com.project.WalletService.model.WalletStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WalletResponse {
    private Long userId;
    private BigDecimal balance;
    private WalletStatus status;
    private LocalDateTime updatedAt;

    public WalletResponse(Long userId, BigDecimal balance, WalletStatus status, LocalDateTime updatedAt) {
        this.userId = userId;
        this.balance = balance;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public WalletResponse() {
    }




    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }



    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public WalletStatus getStatus() {
        return status;
    }

    public void setStatus(WalletStatus status) {
        this.status = status;
    }
}
