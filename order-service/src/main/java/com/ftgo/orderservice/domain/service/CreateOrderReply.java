package com.ftgo.orderservice.domain.service;

import com.ftgo.orderservice.domain.events.OrderState;
import com.ftgo.orderservice.domain.model.Order;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class CreateOrderReply {
    private final Long id;
    private final Long consumerId;
    private final Long restaurantId;
    private final OrderState state;
    private final ZonedDateTime createdAt;

    static CreateOrderReply build(Order order) {
        return CreateOrderReply.builder()
                .id(order.getId())
                .consumerId(order.getConsumerId())
                .restaurantId(order.getRestaurantId())
                .state(order.getState())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
