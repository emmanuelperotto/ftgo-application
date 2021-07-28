package com.ftgo.orderservice.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftgo.orderservice.domain.events.OrderState;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Table("orders")
@Data
@Builder
@Slf4j
@JsonIgnoreProperties(value = {"createdAt"})
public class Order {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Id
    private Long id;

    private OrderState state;

    @CreatedDate
    private ZonedDateTime createdAt;

    private Long consumerId;

    private Long restaurantId;

    private String cid;

    public String toJSON() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON: {}", e.getMessage());
            return null;
        }
    }
}
