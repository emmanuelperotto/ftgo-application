package com.ftgo.consumerservice.domain.service;

import com.ftgo.consumerservice.domain.ConsumerVerificationFailed;
import com.ftgo.consumerservice.domain.model.Consumer;
import com.ftgo.consumerservice.domain.repository.ConsumerRepository;
import reactor.core.publisher.Mono;

public class VerifyConsumerService {
    private final ConsumerRepository consumerRepository;

    VerifyConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    Mono<Consumer> verify(Long consumerId) {
        return consumerRepository
                .findById(consumerId)
                .switchIfEmpty(Mono.error(new ConsumerVerificationFailed("Can't find consumer on DB")));
    }
}
