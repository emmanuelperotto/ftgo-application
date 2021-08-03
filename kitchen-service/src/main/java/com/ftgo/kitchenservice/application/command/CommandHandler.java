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
public class CommandHandler {
    private final CreateTicketReplier createTicketReplier;
    private Command command;

    @SqsListener(value = "KitchenServiceRequestChannel.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(String json, @Headers Map<String, String> headers) {
        try {
            log.info("Will process message: {}", json);
            log.info("Message headers: {}", headers);
            command = Command.buildFromJSON(json);
            log.info("Command parsed {}", command);

            if (command.getName().equals("CreateTicket")) {
//                throw new Exception("Something went wrong!");
                createTicketReplier.replyWithSuccess(command);
            }

        } catch (Exception e) {
            log.error("Couldn't process command: {}", e.getMessage());
            if (command.getName().equals("CreateTicket")) {
                createTicketReplier.replyWithFailure(command, e);
            }
        }
    }
}
