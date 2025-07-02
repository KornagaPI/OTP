package com.frol97.codes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ValidateOtpRequest {
    @JsonProperty("operation_id")
    private String operationId;
    private Integer value;
}
