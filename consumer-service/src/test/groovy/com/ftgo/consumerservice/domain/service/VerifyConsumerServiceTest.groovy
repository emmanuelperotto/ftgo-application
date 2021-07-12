package com.ftgo.consumerservice.domain.service

import com.ftgo.consumerservice.domain.ConsumerVerificationFailed
import com.ftgo.consumerservice.domain.model.Consumer
import com.ftgo.consumerservice.domain.repository.ConsumerRepository
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.ZonedDateTime

class VerifyConsumerServiceTest extends Specification {
    def 'When verification fails'() {
        given:
        def consumerId = 10
        def repoMock = Mock(ConsumerRepository)
        def service = new VerifyConsumerService(repoMock)

        repoMock.findById(10) >> Mono.empty()

        expect:
        StepVerifier
                .create(service.verify(consumerId))
                .expectSubscription()
                .expectError(ConsumerVerificationFailed.class)
                .verify()
    }

    def 'When verification succeeds'() {
        given:
        def consumer = new Consumer(
                id: 10,
                name: "Emmanuel Perotto",
                createdAt: ZonedDateTime.now()
        )

        def repoMock = Mock(ConsumerRepository)
        def service = new VerifyConsumerService(repoMock)

        repoMock.findById(consumer.id) >> Mono.just(consumer)

        expect:
        StepVerifier
                .create(service.verify(consumer.id))
                .expectSubscription()
                .expectNext(consumer)
                .expectComplete()
                .verify()
    }

}
