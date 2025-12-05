package com.project.BillingService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
public class Bill {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String billType;

    private BigDecimal amount;

    private LocalDate generatedDate;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    public Bill(Long id, Long userId, String billType, BigDecimal amount, LocalDate generatedDate, LocalDate dueDate, BillStatus status) {
        this.id = id;
        this.userId = userId;
        this.billType = billType;
        this.amount = amount;
        this.generatedDate = generatedDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Bill() {
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

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }
}
