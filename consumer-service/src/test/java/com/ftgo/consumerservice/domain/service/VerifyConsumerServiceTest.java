package com.ftgo.consumerservice.domain.service;

import com.ftgo.consumerservice.domain.ConsumerVerificationFailed;
import com.ftgo.consumerservice.domain.model.Consumer;
import com.ftgo.consumerservice.domain.repository.ConsumerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.ZonedDateTime;


class VerifyConsumerServiceTest {
    private VerifyConsumerService verifyConsumerService;
    private ConsumerRepository consumerRepository;

    @BeforeEach
    void initMocks() {
        consumerRepository = Mockito.mock(ConsumerRepository.class);
        verifyConsumerService = new VerifyConsumerService(consumerRepository);
    }

    @Test
    void verify_WhenItSucceeds_ItReturnsTheConsumer() {
        var consumer = new Consumer(10L, "Emmanuel Perotto", ZonedDateTime.now());
        Mockito.when(consumerRepository.findById(consumer.getId())).thenReturn(Mono.just(consumer));

        StepVerifier
                .create(verifyConsumerService.verify(consumer.getId()))
                .expectSubscription()
                .expectNext(consumer)
                .verifyComplete();
    }

    @Test
    void verify_WhenItDoesntFindInDB_ItReturnsError() {
        var consumer = new Consumer(10L, "Emmanuel Perotto", ZonedDateTime.now());
        Mockito.when(consumerRepository.findById(consumer.getId())).thenReturn(Mono.empty());

        StepVerifier
                .create(verifyConsumerService.verify(consumer.getId()))
                .expectSubscription()
                .expectErrorMatches(e ->
                        e.getClass() == ConsumerVerificationFailed.class &&
                                e.getMessage().equals("Can't find consumer on DB")
                ).verify();

    }
}