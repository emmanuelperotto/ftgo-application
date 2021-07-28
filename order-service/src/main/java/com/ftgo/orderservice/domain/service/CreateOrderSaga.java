package com.ftgo.orderservice.domain.service;

import com.ftgo.orderservice.domain.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.StartExecutionRequest;

import java.util.UUID;
import java.util.function.Function;

@Service
@Slf4j
public class CreateOrderSaga {
    private SfnClient sfnClient;

    public CreateOrderSaga(SfnClient sfnClient) {
        this.sfnClient = sfnClient;
    }

    public Mono<Order> create(Order order) {
        order.setCid(UUID.randomUUID().toString());

        return Mono.just(order)
                .map(startSfnStateMachine);
    }

    private final Function<Order, Order> startSfnStateMachine = (order) -> {
        var STATE_MACHINE_ARN = System.getenv("STATE_MACHINE_ARN");
        var json = order.toJSON();

        if (json == null) {
            throw new RuntimeException("Error parsing JSON");
        }

        var request = StartExecutionRequest.builder()
                .input(json)
                .name(order.getCid())
                .stateMachineArn(STATE_MACHINE_ARN)
                .build();

        this.sfnClient.startExecution(request);
        return order;
    };
}
