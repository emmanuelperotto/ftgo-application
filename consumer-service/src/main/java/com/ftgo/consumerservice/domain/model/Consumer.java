package com.ftgo.consumerservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Data
@Table("consumers")
@RequiredArgsConstructor
public class Consumer {
    @Id
    private final Long id;

    private final String name;

    @CreatedDate
    private final ZonedDateTime createdAt;
}
