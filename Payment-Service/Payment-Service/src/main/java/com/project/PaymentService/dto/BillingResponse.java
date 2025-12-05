package com.project.PaymentService.dto;

import com.project.PaymentService.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillingResponse {

    private Long id;
    private Long userId;
    private String billType;
    private BigDecimal amount;
    private LocalDate generatedDate;
    private LocalDate dueDate;
    private PaymentStatus status;

    public BillingResponse(Long id, Long userId, String billType, BigDecimal amount, LocalDate generatedDate, LocalDate dueDate, PaymentStatus status) {
        this.id = id;
        this.userId = userId;
        this.billType = billType;
        this.amount = amount;
        this.generatedDate = generatedDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public BillingResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
