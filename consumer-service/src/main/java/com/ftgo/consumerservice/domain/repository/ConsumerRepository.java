package com.ftgo.consumerservice.domain.repository;

import com.ftgo.consumerservice.domain.model.Consumer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ConsumerRepository extends ReactiveCrudRepository<Consumer, Long> {
}
