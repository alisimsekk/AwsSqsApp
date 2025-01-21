package com.simsekali.awssqsdemo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserCreateResponse {
    private Long userId;
    private String email;
    private String validationToken;
}
