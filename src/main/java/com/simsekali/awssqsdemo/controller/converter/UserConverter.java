package com.simsekali.awssqsdemo.controller.converter;

import com.simsekali.awssqsdemo.controller.dto.UserCreateResponse;
import com.simsekali.awssqsdemo.controller.dto.UserDto;
import com.simsekali.awssqsdemo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    public UserCreateResponse toUserCreateResponse(User user) {
        return UserCreateResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .validationToken(user.getValidationToken())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isActive(user.isActive())
                .build();
    }
}
