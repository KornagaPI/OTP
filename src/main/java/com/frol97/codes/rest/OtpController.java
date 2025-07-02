package com.frol97.codes.rest;


import org.springframework.web.bind.annotation.*;

import com.frol97.codes.dto.GenerateOtpRequest;
import com.frol97.codes.dto.ResponseWithMessage;
import com.frol97.codes.dto.ValidateOtpRequest;
import com.frol97.codes.dto.ValidateOtpResponse;
import com.frol97.codes.service.OtpService;

@RestController
@RequestMapping("/api")
public class OtpController {
    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/otp/generate")
    public ResponseWithMessage generateOtp(@RequestBody GenerateOtpRequest generateOtpRequest) {
        return otpService.generateOtp(generateOtpRequest);
    }

    @PostMapping("/otp/validate")
    public ValidateOtpResponse validateOtp(@RequestBody ValidateOtpRequest request) {
        return otpService.validateOtp(request);
    }
}
