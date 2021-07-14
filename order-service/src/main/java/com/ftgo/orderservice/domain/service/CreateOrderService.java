package com.ftgo.orderservice.domain.service;

import com.ftgo.orderservice.domain.OrderRepository;
import com.ftgo.orderservice.domain.events.OrderState;
import com.ftgo.orderservice.domain.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.function.Function;

@Service
@Slf4j
public class CreateOrderService {
    private OrderRepository orderRepository;

    public CreateOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Mono<CreateOrderReply> create(CreateOrderRequest orderRequest) {
        return Mono.just(orderRequest).map(buildOrderObject)
                .flatMap(persistOrder)
//                .flatMap(createOrderSaga) //TODO: WIP
                .map(buildOrderReplyObject);
    }

    private final Function<CreateOrderRequest, Order> buildOrderObject = (orderRequest) -> Order.builder()
            .consumerId(orderRequest.getConsumerId())
            .restaurantId(orderRequest.getRestaurantId())
            .state(OrderState.APPROVAL_PENDING)
            .createdAt(ZonedDateTime.now())
            .build();

    private final Function<Order, Mono<Order>> persistOrder = (order) -> orderRepository.save(order)
            .doOnError(e -> log.warn("Error saving order to db: " + e.getMessage()));

    private final Function<Order, CreateOrderReply> buildOrderReplyObject = CreateOrderReply::build;
}
