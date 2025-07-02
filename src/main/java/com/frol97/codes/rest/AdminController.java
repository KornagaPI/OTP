package com.frol97.codes.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.frol97.codes.dto.DeleteUserRequest;
import com.frol97.codes.dto.GetUserResponse;
import com.frol97.codes.dto.ResponseWithMessage;
import com.frol97.codes.dto.SetOtpConfigRequest;
import com.frol97.codes.model.entity.OtpConfig;
import com.frol97.codes.service.OtpConfigService;
import com.frol97.codes.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final OtpConfigService otpConfigService;
    private final UserService userService;

    @Autowired
    public AdminController(OtpConfigService otpConfigService, UserService userService) {
        this.otpConfigService = otpConfigService;
        this.userService = userService;
    }

    @GetMapping("/get-otp-config")
    public OtpConfig getOtpConfig() {
        return otpConfigService.get();
    }

    @PostMapping("/set-otp-config")
    public OtpConfig setOtpConfig(@RequestBody SetOtpConfigRequest request) {
        return otpConfigService.set(request);
    }

    @GetMapping("/get-users")
    public List<GetUserResponse> getUsers() {
        return userService.getUsersInfo();
    }

    @PostMapping("/delete-user")
    public ResponseEntity<ResponseWithMessage> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) {
        return  userService.deleteUser(deleteUserRequest);
    }
}
