package com.simsekali.awssqsdemo.aws.listener;

import com.simsekali.awssqsdemo.aws.SQSQueueMessageRequest;
import com.simsekali.awssqsdemo.controller.dto.UserActivationDto;
import com.simsekali.awssqsdemo.service.UserService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode.ON_SUCCESS;


@Slf4j
@RequiredArgsConstructor
@Component
public class UserSignUpQueueListener {

    private final UserService userService;

    @SqsListener(value = "${spring.cloud.aws.sqs.userSignUp.source}", acknowledgementMode = ON_SUCCESS)
    @Transactional
    public void receiveSmsMessage(SQSQueueMessageRequest message) {
        try {
            log.info("New registered user received from the source queue {}", message.getBody());
            UserActivationDto activatedUser = userService.activateUser(message.getBody().getValidationToken());

            if (message.isDeadLetterQueueTest()) { // testing for deadletter queue
                int x = 5/0;
            }

            log.info("The user is activated for email {}", activatedUser.getActivatedUser().getEmail());
        } catch (ArithmeticException e) {
            log.error("Error while processing the source queue for user email: {}", message.getBody().getEmail());
            throw new RuntimeException(e.getMessage());
        }
    }

    @SqsListener(value = "${spring.cloud.aws.sqs.userSignUp.deadletter}", acknowledgementMode = ON_SUCCESS)
    public void listenDeadletter(SQSQueueMessageRequest message) {
        try {
            log.info("New registered user received from DeadLetter queue {}", message.getBody());
            UserActivationDto activatedUser = userService.activateUser(message.getBody().getValidationToken());
            log.info("The user is activated for email {}", activatedUser.getActivatedUser().getEmail());
        } catch (Exception e) {
            log.error("Error while processing the deadLetter queue for user email: {}", message.getBody().getEmail(), e);
        }
    }
}