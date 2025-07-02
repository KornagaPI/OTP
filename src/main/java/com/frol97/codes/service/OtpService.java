package com.frol97.codes.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.frol97.codes.configuration.exception.RequestException;
import com.frol97.codes.dto.GenerateOtpRequest;
import com.frol97.codes.dto.ResponseWithMessage;
import com.frol97.codes.dto.ValidateOtpRequest;
import com.frol97.codes.dto.ValidateOtpResponse;
import com.frol97.codes.model.entity.Otp;
import com.frol97.codes.model.entity.OtpState;
import com.frol97.codes.model.entity.User;
import com.frol97.codes.model.repo.OtpConfigRepo;
import com.frol97.codes.model.repo.OtpRepo;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;;

@Service
@Transactional
public class OtpService {
    private final OtpRepo repo;
    private final OtpConfigRepo otpConfigRepo;
    private final OtpDispatcherService otpDispatcherService;

    @Autowired
    public OtpService(OtpRepo repo, OtpConfigRepo otpConfigRepo, OtpDispatcherService otpDispatcherService) {
        this.repo = repo;
        this.otpConfigRepo = otpConfigRepo;
        this.otpDispatcherService = otpDispatcherService;
    }

    public List<Otp> getUserOtps(String login) {
        return repo.findByUserLogin(login).orElseThrow();
    }

    public long deleteOtpsForUser(String login) {
        return repo.deleteByUserLogin(login);
    }

    public ResponseWithMessage generateOtp(GenerateOtpRequest generateOtpRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var otpConfig = otpConfigRepo.findById(0)
                .orElseThrow(() -> new RequestException("Не удалось найти конфигурацию OTP"));

        if (otpConfig.getLength() <= 0) {
            throw new RequestException("В настройках указана неверная длина OTP кода");
        }

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder otpString = new StringBuilder();

        for (int i = 0; i < otpConfig.getLength(); i++) {
            otpString.append(secureRandom.nextInt(10));
        }

        Otp otp = new Otp(
            generateOtpRequest.getOperationId(),
            Integer.parseInt(otpString.toString()),
            OtpState.ACTIVE,
            user,
            LocalDateTime.now().plusSeconds(otpConfig.getLifetime())
        );
        repo.save(otp);
        otpDispatcherService.sendOtp(user.getChannelType(), user.getOtpDestination(), otp.getValue());

        return new ResponseWithMessage("OTP код отправлен");
    }

    public ValidateOtpResponse validateOtp(ValidateOtpRequest request) {
        ValidateOtpResponse response = new ValidateOtpResponse(null, false);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        repo.findByUserLogin(user.getLogin()).ifPresent(
                otps -> otps.stream().filter(
                        otp -> otp.getOperationId().equals(request.getOperationId())
                            && otp.getValue().equals(request.getValue()))
                        .findFirst().ifPresent(otp -> {
                            if (otp.getState() == OtpState.ACTIVE) {
                                var now = LocalDateTime.now();
                                if (otp.getExpiresAt().isAfter(now)) {
                                    otp.setUsedAt(now);
                                    otp.setState(OtpState.USED);
                                    repo.save(otp);
                                    response.setValid(true);
                                }
                            }
                            response.setOtp(otp);
                        }));
        return response;
    }
}
