package com.ftgo.kitchenservice.application.command;

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
public class CreateTicketCommandHandler {
    private final CreateTicketReplier replier;
    private CreateTicketCommand command;

    @SqsListener(value = "KitchenServiceRequestChannel.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(String json, @Headers Map<String, String> headers) {
        try {
            log.info("Will process message: {}", json);
            log.info("Message headers: {}", headers);
            command = CreateTicketCommand.buildFromJSON(json);
            log.info("Command parsed {}", command);

            replier.replyWithSuccess(command);
        } catch (Exception e) {
            replier.replyWithFailure(command, e);
        }
    }
}
