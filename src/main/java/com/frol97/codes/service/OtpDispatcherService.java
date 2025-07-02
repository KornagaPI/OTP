package com.frol97.codes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frol97.codes.model.entity.ChannelType;
import com.frol97.codes.service.senders.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class OtpDispatcherService {
    private final List<OtpSender> senders;

    @Autowired
    public OtpDispatcherService(
            EmailOtpSender emailOtpSender,
            FileOtpSender fileOtpSender,
            SmsOtpSender smsOtpSender,
            TelegramOtpSender telegramOtpSender) {
        senders = new ArrayList<>();
        senders.add(emailOtpSender);
        senders.add(fileOtpSender);
        senders.add(smsOtpSender);
        senders.add(telegramOtpSender);
    }

    public void sendOtp(ChannelType channel, String destination, int code) {
        senders.stream()
                .filter(s -> s.getChannelType() == channel)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported channel"))
                .sendOtp(destination, code);
    }
}
