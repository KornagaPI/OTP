package com.frol97.codes.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SetOtpConfigRequest {
    private Integer lifetime;
    private Integer length;
}
