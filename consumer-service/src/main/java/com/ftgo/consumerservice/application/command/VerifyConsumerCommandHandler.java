package com.ftgo.consumerservice.application.command;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class VerifyConsumerCommandHandler {
    private final VerifyConsumerReplier replier;
    private VerifyConsumerCommand command;

    @SqsListener(value = "ConsumerServiceRequestChannel.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(String json, @Headers Map<String, String> headers) {
        try {
            log.info("Will proccess message: {}", json);
            log.info("Message headers: {}", headers);
            command = VerifyConsumerCommand.buildFromJSON(json);
            log.info("Command parsed {}", command);

            replier.replyWithSuccess(command);
        } catch (Exception e) {
            replier.replyWithFailure(command, e);
        }
    }
}
