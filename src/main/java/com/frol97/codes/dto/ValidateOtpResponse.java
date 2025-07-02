package com.frol97.codes.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.frol97.codes.model.entity.Otp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidateOtpResponse {
    private Otp otp;
    @JsonProperty("is_valid")
    private boolean isValid;
}
