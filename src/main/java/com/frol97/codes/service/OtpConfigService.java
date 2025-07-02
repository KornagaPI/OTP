package com.frol97.codes.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frol97.codes.dto.SetOtpConfigRequest;
import com.frol97.codes.model.entity.OtpConfig;
import com.frol97.codes.model.repo.OtpConfigRepo;

@Service
@Transactional
public class OtpConfigService {
    @Autowired
    OtpConfigRepo repo;

    public OtpConfig get() {
        return repo.findById(0).orElse(null);
    }

    public OtpConfig set(SetOtpConfigRequest request) {
        var otpConfig = repo.findById(0).orElseThrow();
        otpConfig.setLength(request.getLength());
        otpConfig.setLifetime(request.getLifetime());
        repo.save(otpConfig);
        return otpConfig;
    }
}
