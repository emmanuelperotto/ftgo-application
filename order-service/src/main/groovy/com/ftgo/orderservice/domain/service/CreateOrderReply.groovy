package com.ftgo.orderservice.domain.service

import com.ftgo.orderservice.domain.events.OrderState
import com.ftgo.orderservice.domain.model.Order
import groovy.transform.Immutable

import java.time.ZonedDateTime

@Immutable
class CreateOrderReply {
    Long id
    Long consumerId
    Long restaurantId
    OrderState state
    ZonedDateTime createdAt

    static CreateOrderReply build(Order order) {
        new CreateOrderReply(
                id: order.id,
                state: order.state,
                consumerId: order.consumerId,
                restaurantId: order.restaurantId,
                createdAt: order.createdAt
        )
    }
}
