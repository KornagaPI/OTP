package com.frol97.codes.model.repo;

import org.springframework.data.repository.CrudRepository;

import com.frol97.codes.model.entity.Otp;
import com.frol97.codes.model.entity.OtpState;

import java.util.List;
import java.util.Optional;

public interface OtpRepo extends CrudRepository<Otp, Long> {
    Optional<List<Otp>> findByUserLogin(String login);
    long deleteByUserLogin(String login);
    Optional<List<Otp>> findByState(OtpState state);
}
