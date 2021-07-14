package com.ftgo.orderservice.domain.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.With;

@Data
@With
@JsonIgnoreProperties(value = {"consumerId"})
public class CreateOrderRequest {
    private final Long consumerId;
    private final Long restaurantId;
}
