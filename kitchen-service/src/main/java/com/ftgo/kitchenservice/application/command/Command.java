package com.ftgo.kitchenservice.application.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Command {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String name;
    private final Long orderId;
    private final Long consumerId;
    private final Long restaurantId;
    private final String taskToken;
    private final String cid;

    public String taskToken() {
        return taskToken;
    }

    public static Command buildFromJSON(String json) throws JsonProcessingException {
        var jsonNode = objectMapper.readTree(json);
        var name = jsonNode.get("command").asText();
        var commandData = jsonNode.get("data");

        var cid = commandData.get("cid").asText();
        var orderId = commandData.get("orderId").asLong();
        var consumerId = commandData.get("consumerId").asLong();
        var restaurantId = commandData.get("restaurantId").asLong();
        var taskToken = jsonNode.get("taskToken");

        return Command.builder()
                .name(name)
                .orderId(orderId)
                .consumerId(consumerId)
                .restaurantId(restaurantId)
                .taskToken(taskToken != null ? taskToken.asText() : null)
                .cid(cid)
                .build();
    }
}
