package com.simsekali.awssqsdemo.controller.dto;

import com.simsekali.awssqsdemo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserActivationDto {
    private User activatedUser;
}
