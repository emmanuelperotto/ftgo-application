package com.ftgo.orderservice.application.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandHandler {
    @SqsListener(value = "OrderServiceRequestChannel.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(String json, @Headers Map<String, String> headers) throws JsonProcessingException {
        log.info("Will process message: {}", json);
        log.info("Message headers: {}", headers);
        Command command = Command.buildFromJSON(json);
        log.info("Command parsed {}", command);
    }
}
