package com.ftgo.consumerservice.domain;

public class ConsumerVerificationFailed extends RuntimeException {
    public ConsumerVerificationFailed(String message) {
        super(message);
    }
}
