package com.ftgo.orderservice.application.controller

import com.ftgo.orderservice.domain.service.CreateOrderReply
import com.ftgo.orderservice.domain.service.CreateOrderRequest
import com.ftgo.orderservice.domain.service.CreateOrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

import java.util.logging.Logger

@RestController
@RequestMapping("/orders")
class OrdersController {
    private static final LOGGER = Logger.getLogger("OrdersController")
    private CreateOrderService createOrderService

    OrdersController(CreateOrderService createOrderService) {
        this.createOrderService = createOrderService
    }

    @PostMapping
    Mono<ResponseEntity<CreateOrderReply>> create(@RequestHeader("consumer-id") Long consumerId, @RequestHeader("restaurant-id") Long restaurantId) {
        def orderRequest = new CreateOrderRequest(consumerId: consumerId, restaurantId: restaurantId)
        LOGGER.info("Request received to create order with consumerId: ${orderRequest.consumerId} and restaurantId: ${orderRequest.restaurantId}")

        createOrderService.create(orderRequest)
                .map(o -> ResponseEntity.created(null).body(o))
                .onErrorResume(e -> {
                    Mono.just(ResponseEntity.unprocessableEntity().body(e.message))
                })
    }
}