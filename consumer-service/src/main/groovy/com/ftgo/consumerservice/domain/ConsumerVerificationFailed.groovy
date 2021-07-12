package com.ftgo.consumerservice.domain

class ConsumerVerificationFailed extends RuntimeException {

    ConsumerVerificationFailed(String message) {
        super(message)
    }
}
