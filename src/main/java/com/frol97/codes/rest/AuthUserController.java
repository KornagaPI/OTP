package com.frol97.codes.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frol97.codes.dto.JwtAuthenticationResponse;
import com.frol97.codes.dto.SignInRequest;
import com.frol97.codes.dto.RegisterUserRequest;
import com.frol97.codes.service.AuthenticationService;

@RestController
@RequestMapping("/v1/user")
public class AuthUserController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthUserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody RegisterUserRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
