package com.simsekali.awssqsdemo.service;

import com.simsekali.awssqsdemo.controller.converter.UserConverter;
import com.simsekali.awssqsdemo.controller.dto.AccountValidationResponse;
import com.simsekali.awssqsdemo.controller.dto.UserCreateRequest;
import com.simsekali.awssqsdemo.controller.dto.UserCreateResponse;
import com.simsekali.awssqsdemo.entity.User;
import com.simsekali.awssqsdemo.exception.UserRegistrationException;
import com.simsekali.awssqsdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final String VALIDATION_SUCCESS_MESSAGE ="Your account is validated successfully";

    public UserCreateResponse registerUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserRegistrationException("Email already registered");
        }
        User user = User.create(request);
        User savedUser = userRepository.save(user);
        return userConverter.toUserCreateResponse(savedUser);
    }

    public AccountValidationResponse validateAccount(String token) {
        User user = userRepository.findByValidationTokenAndValidationTokenExpiryAfter(token, LocalDateTime.now())
                .orElseThrow(() -> new UserRegistrationException("Invalid or expired validation token"));

        user.validateUser();
        userRepository.save(user);
        return new AccountValidationResponse(VALIDATION_SUCCESS_MESSAGE);
    }
}
