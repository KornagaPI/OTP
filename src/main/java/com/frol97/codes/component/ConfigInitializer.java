package com.frol97.codes.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.frol97.codes.model.entity.OtpConfig;
import com.frol97.codes.model.repo.OtpConfigRepo;

@Component
public class ConfigInitializer implements ApplicationRunner {

    private final OtpConfigRepo otpConfigRepo;

    @Autowired
    public ConfigInitializer(OtpConfigRepo otpConfigRepo) {
        this.otpConfigRepo = otpConfigRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!otpConfigRepo.existsById(0)) {
            otpConfigRepo.save(new OtpConfig());
        }
    }
}
