package com.project.RazorpayPayment.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;


public class RazorpayWebhookDTO {


    private String event;
    private JsonNode payload;

    public RazorpayWebhookDTO(String event, JsonNode payload) {
        this.event = event;
        this.payload = payload;
    }

    public RazorpayWebhookDTO() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }
}
