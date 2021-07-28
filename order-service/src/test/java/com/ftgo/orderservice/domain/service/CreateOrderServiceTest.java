package com.ftgo.orderservice.domain.service;

import com.ftgo.orderservice.domain.OrderRepository;
import com.ftgo.orderservice.domain.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CreateOrderServiceTest {
    private CreateOrderService createOrderService;
    private OrderRepository orderRepositoryMock;
    private CreateOrderSaga createOrderSagaMock;

    @BeforeEach
    void setUp() {
        createOrderSagaMock = Mockito.mock(CreateOrderSaga.class);
        orderRepositoryMock = Mockito.mock(OrderRepository.class);
        createOrderService = new CreateOrderService(orderRepositoryMock, createOrderSagaMock);
    }

    @Test
    void create_WhenItSucceeds_ThenReturnASuccessfulReply() {
        var order = Order.builder().build();
        Mockito.when(orderRepositoryMock.save(Mockito.any())).thenReturn(Mono.just(order));
        Mockito.when(createOrderSagaMock.create(Mockito.any())).thenReturn(Mono.just(order));

        var createRequest = new CreateOrderRequest(1L, 2L);

        StepVerifier.create(createOrderService.create(createRequest))
                .expectSubscription()
                .expectNext(CreateOrderReply.build(order))
                .verifyComplete();
    }

    @Test
    void create_WhenItFails_ThenReturnsError() {
        Mockito.when(orderRepositoryMock.save(Mockito.any())).thenThrow(IllegalArgumentException.class);

        var createRequest = new CreateOrderRequest(1L, 2L);
        StepVerifier.create(createOrderService.create(createRequest))
            .expectSubscription()
            .verifyError();
    }
}