package com.simsekali.awssqsdemo.aws;

import com.simsekali.awssqsdemo.entity.User;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsSQSManagementService {

    @Value("${spring.cloud.aws.sqs.userSignUp.source}")
    private String demoQueue;
    private final SqsTemplate sqsTemplate;
    private static final String QUEUE_MESSAGE_TYPE_NAME = "USER_SIGN_UP";


    public void sendMessage(SQSQueueMessageRequest queueMessage) {
        try {
            sqsTemplate
                    .send(sqsSendOptions ->
                            sqsSendOptions
                                    .queue(demoQueue)
                                    .payload(queueMessage)
                                    .header("x-queue-message-type", QUEUE_MESSAGE_TYPE_NAME)
                                    .messageGroupId(queueMessage.getBody().getId().toString())
                                    .messageDeduplicationId(queueMessage.getBody().getId().toString().concat("-").concat(UUID.randomUUID().toString().replace("-", "")))
                    );
            log.info("SQSQueueService send operation has succeeded for user email: " + queueMessage.getBody().getEmail());
        } catch (Exception e) {
            log.error("SQSQueueService send operation has failed for user email: " + queueMessage.getBody().getEmail(), e);
            throw new RuntimeException(e);
        }
    }
}
