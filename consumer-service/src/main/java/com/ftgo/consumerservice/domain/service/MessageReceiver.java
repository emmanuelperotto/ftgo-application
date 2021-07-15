package com.ftgo.consumerservice.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class MessageReceiver {

    private ObjectMapper objectMapper;

    public MessageReceiver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Data
    @AllArgsConstructor
    public static class Event {
        private Long orderId;
        private String command;
        private String taskToken;
    }

    @SqsListener(value = "ConsumerServiceRequestChannel.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(String rawMessage,
                               @Headers Map<String, String> headers) throws JsonProcessingException {

        var json = objectMapper.readTree(rawMessage);
        log.info("headers {}",headers);
        log.info("message body {}", rawMessage);
        log.info("orderId: {}", headers.get("OrderId"));

        log.info("json parsed: {}", json);
        var event = new Event(json.get("Input").get("OrderId").asLong(), json.get("Input").get("Command").asText(), json.get("TaskToken").asText());
        log.info("event parsed: {}", event);
    }
}
