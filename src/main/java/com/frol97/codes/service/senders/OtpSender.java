package com.frol97.codes.service.senders;

import com.frol97.codes.model.entity.ChannelType;

public interface OtpSender {
    void sendOtp(String destination, int code);
    ChannelType getChannelType();
}
