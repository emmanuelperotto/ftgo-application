package com.ftgo.orderservice.domain.service

import com.ftgo.orderservice.domain.OrderRepository
import com.ftgo.orderservice.domain.model.Order
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

import java.time.ZonedDateTime
import java.util.logging.Level
import java.util.logging.Logger

@Service
class CreateOrderService {
    private static final LOGGER = Logger.getLogger("CreateOrderService")
    private OrderRepository orderRepository

    CreateOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository
    }

    Mono<CreateOrderReply> create(CreateOrderRequest orderRequest) {
        Mono.just(orderRequest)
                .map(buildOrderObject)
                .flatMap(persistOrder)
                .map(buildOrderReplyObject)
    }

    private Closure<Order> buildOrderObject = (CreateOrderRequest orderRequest) -> {
        return new Order(createdAt: ZonedDateTime.now(), consumerId: orderRequest.consumerId, restaurantId: orderRequest.restaurantId)
    }

    private Closure<CreateOrderReply> buildOrderReplyObject = (Order order) -> {
        return CreateOrderReply.build(order)
    }

    private Closure<Mono<Order>> persistOrder = (Order order) -> {
        orderRepository.save(order)
                .doOnError(e -> {
                    LOGGER.log(Level.WARNING, "Error saving order to db: ${e.message}", order)
                })
    }
}
