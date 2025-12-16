package com.project.PaymentService.dto;

import lombok.Builder;


public class PaymentConfirmationRequest {

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private boolean success;

    public PaymentConfirmationRequest(String razorpayOrderId, String razorpayPaymentId, boolean success) {
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.success = success;
    }

    public PaymentConfirmationRequest() {
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
