package com.ftgo.orderservice.domain;

import com.ftgo.orderservice.domain.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
}
