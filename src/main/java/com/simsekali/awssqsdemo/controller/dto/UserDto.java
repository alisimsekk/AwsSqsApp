package com.simsekali.awssqsdemo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private boolean isActive;
}
