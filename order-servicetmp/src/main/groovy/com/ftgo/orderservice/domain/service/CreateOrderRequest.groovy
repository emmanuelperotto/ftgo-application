package com.ftgo.orderservice.domain.service

import groovy.transform.Immutable

@Immutable
class CreateOrderRequest {
    Long consumerId
    Long restaurantId
}
