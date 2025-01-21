package com.simsekali.awssqsdemo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountValidationResponse {
    private String validationMessage;
}
