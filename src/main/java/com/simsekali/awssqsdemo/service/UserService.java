package com.simsekali.awssqsdemo.service;

import com.simsekali.awssqsdemo.aws.AwsSQSManagementService;
import com.simsekali.awssqsdemo.aws.SQSQueueMessageRequest;
import com.simsekali.awssqsdemo.controller.converter.UserConverter;
import com.simsekali.awssqsdemo.controller.dto.UserActivationDto;
import com.simsekali.awssqsdemo.controller.dto.UserCreateRequest;
import com.simsekali.awssqsdemo.controller.dto.UserCreateResponse;
import com.simsekali.awssqsdemo.controller.dto.UserDto;
import com.simsekali.awssqsdemo.entity.User;
import com.simsekali.awssqsdemo.exception.UserActivationException;
import com.simsekali.awssqsdemo.exception.UserNotFoundExecption;
import com.simsekali.awssqsdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final AwsSQSManagementService awsSQSManagementService;

    public UserCreateResponse registerUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserActivationException("Email already registered");
        }
        User user = User.create(request);
        User savedUser = userRepository.save(user);
        sendMessageToQueue(savedUser, request);
        log.info("User is saved successfully {}", savedUser);
        return userConverter.toUserCreateResponse(savedUser);
    }

    private void sendMessageToQueue(User user, UserCreateRequest request) {
        SQSQueueMessageRequest queueMessage = SQSQueueMessageRequest.builder()
                .body(user)
                .isDeadLetterQueueTest(request.isDeadLetterQueueTest())
                .build();
        awsSQSManagementService.sendMessage(queueMessage);
    }

    public UserActivationDto activateUser(String token) {
        User user = userRepository.findByValidationTokenAndValidationTokenExpiryAfter(token, LocalDateTime.now())
                .orElseThrow(() -> new UserActivationException("Invalid or expired validation token " + token));

        user.activateUser();
        User activatedUser = userRepository.save(user);
        log.info("User {} is activated successfully", activatedUser.getEmail());
        return new UserActivationDto(activatedUser);
    }

    public UserDto findByEmail(String email) {
        User fromDb = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundExecption(String.format("User not found with email %s", email)));
        return userConverter.toUserDto(fromDb);
    }
}
