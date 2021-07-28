package com.ftgo.orderservice.application.controller;

import com.ftgo.orderservice.domain.service.CreateOrderReply;
import com.ftgo.orderservice.domain.service.CreateOrderRequest;
import com.ftgo.orderservice.domain.service.CreateOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final CreateOrderService createOrderService;

    public OrdersController(CreateOrderService createOrderService) {
        this.createOrderService = createOrderService;
    }

    @PostMapping
    Mono<ResponseEntity<CreateOrderReply>> createOrder(@RequestHeader("consumer-id") Long consumerId, @RequestBody CreateOrderRequest requestBody) {
        var createOrderRequest = requestBody.withConsumerId(consumerId);

        return createOrderService.create(createOrderRequest)
                .map(createOrderReply -> ResponseEntity.created(null).body(createOrderReply))
                .onErrorResume(e -> {
                    log.error("Error creating order: {}", e.getMessage());

                    return Mono.just(ResponseEntity.unprocessableEntity().body(null));
                });
    }

}
