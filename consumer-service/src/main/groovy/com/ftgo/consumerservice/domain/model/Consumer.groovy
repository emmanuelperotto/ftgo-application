package com.ftgo.consumerservice.domain.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

import java.time.ZonedDateTime

@Table("consumers")
class Consumer {
    @Id
    Long id

    String name

    @CreatedDate
    ZonedDateTime createdAt
}
