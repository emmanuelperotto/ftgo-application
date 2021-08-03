package com.ftgo.accountingservice.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.SendTaskFailureRequest;
import software.amazon.awssdk.services.sfn.model.SendTaskSuccessRequest;
import software.amazon.awssdk.services.sfn.model.TaskTimedOutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizeCardReplier {
    private final SfnClient sfnClient;

    public void replyWithSuccess(AuthorizeCardCommand authorizeCardCommand) {
        try {
            var reply = AuthorizeCardReply.buildFromCommand(authorizeCardCommand);
            log.info("Reply built: {}", reply);
            var replyJSON = reply.toJSON();
            log.info("Reply JSON built: {}", replyJSON);

            var request = SendTaskSuccessRequest.builder()
                    .taskToken(authorizeCardCommand.taskToken())
                    .output(replyJSON)
                    .build();
            sfnClient.sendTaskSuccess(request);
        } catch (Exception e) {
            if (e instanceof TaskTimedOutException) {
                log.info("Task timed out, skipping sfn notification");
                return;
            }

            log.error("Error trying to reply VerifyConsumerCommand: {}", e.getMessage());
            replyWithFailure(authorizeCardCommand, e);
        }
    }

    public void replyWithFailure(AuthorizeCardCommand authorizeCardCommand, Exception e) {
        try {
            var cause = e.getCause();
            var message = e.getMessage();

            log.info("Replying command with failure: " + e);
            var request = SendTaskFailureRequest.builder()
                    .error(message)
                    .cause(cause != null ? cause.getMessage() : "")
                    .taskToken(authorizeCardCommand.taskToken())
                    .build();
            sfnClient.sendTaskFailure(request);
        } catch (Exception ex) {
            log.error("Error replying failure: {}", ex.getMessage());
        }
    }
}
