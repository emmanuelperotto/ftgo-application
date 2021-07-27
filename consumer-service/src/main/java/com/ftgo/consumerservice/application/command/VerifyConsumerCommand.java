package com.ftgo.consumerservice.application.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyConsumerCommand {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Long orderId;
    private final Long consumerId;
    private final Long restaurantId;
    private final String taskToken;
    private final String cid;

    public String taskToken() {
        return taskToken;
    }

    public static VerifyConsumerCommand buildFromJSON(String json) throws JsonProcessingException {
        var jsonNode = objectMapper.readTree(json);
        var commandData = jsonNode.get("data");
        var cid = commandData.get("cid").asText();
        var orderId = commandData.get("orderId").asLong();
        var consumerId = commandData.get("consumerId").asLong();
        var restaurantId = commandData.get("restaurantId").asLong();
        var taskToken = jsonNode.get("taskToken").asText();

        return VerifyConsumerCommand.builder()
                .orderId(orderId)
                .consumerId(consumerId)
                .restaurantId(restaurantId)
                .taskToken(taskToken)
                .cid(cid)
                .build();
    }
}
