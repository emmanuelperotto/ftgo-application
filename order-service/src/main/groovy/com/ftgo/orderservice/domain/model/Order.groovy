package com.ftgo.orderservice.domain.model

import com.ftgo.orderservice.domain.events.OrderState
import groovy.transform.ToString
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

import java.time.ZonedDateTime

@Table("orders")
@ToString
class Order {
    @Id
    Long id

    OrderState state

    @CreatedDate
    ZonedDateTime createdAt

    Long consumerId
    Long restaurantId

    Order() {
        this.state = OrderState.APPROVAL_PENDING
    }
}
