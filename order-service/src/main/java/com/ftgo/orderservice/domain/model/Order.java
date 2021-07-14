package com.ftgo.orderservice.domain.model;

import com.ftgo.orderservice.domain.events.OrderState;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Table("orders")
@Data
@Builder
public class Order {
    @Id
    private Long id;

    private OrderState state;

    @CreatedDate
    private ZonedDateTime createdAt;

    private Long consumerId;

    private Long restaurantId;
}
