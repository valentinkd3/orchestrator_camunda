package com.example.orchestrator.constant;

public enum ProcessStage {

    START_PROCESS_KEY("orchestrator_process"),

    ORDER_ID("orderId"),

    ERROR("error"),

    PRICE("price"),

    TRANSACTION_ERROR("transactionError"),

    REQUEST_BODY("request_body");
    private String value;

    ProcessStage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
