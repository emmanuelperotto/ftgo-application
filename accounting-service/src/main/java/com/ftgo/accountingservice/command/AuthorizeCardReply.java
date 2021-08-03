package com.ftgo.accountingservice.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(value = {"objectMapper"})
public class AuthorizeCardReply {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Long orderId;
    private final Long consumerId;
    private final Long restaurantId;
    private final String cid;

    String toJSON() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }

    public static AuthorizeCardReply buildFromCommand(AuthorizeCardCommand command) {
        return AuthorizeCardReply.builder()
                .orderId(command.getOrderId())
                .consumerId(command.getConsumerId())
                .restaurantId(command.getRestaurantId())
                .cid(command.getCid())
                .build();
    }
}
