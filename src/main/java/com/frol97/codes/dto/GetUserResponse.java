package com.frol97.codes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import com.frol97.codes.model.entity.Otp;


@Getter
@Setter
@AllArgsConstructor
public class GetUserResponse {
    public String login;
    public String role;
    public List<Otp> otps;
}
