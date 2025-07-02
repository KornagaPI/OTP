package com.frol97.codes.service.senders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.frol97.codes.model.entity.ChannelType;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelegramOtpSender implements OtpSender {
    private final RestTemplate restTemplate;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public void sendOtp(String chatId, int code) {
        try {
            String url = String.format(
                    "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                    botToken,
                    chatId,
                    "Your OTP code: " + code
            );

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (!(response).getStatusCode().is2xxSuccessful()) {
                log.error("Telegram API error: {}", response.getBody());
            }

        } catch (Exception e) {
            log.error("Error sending Telegram message", e);
            throw new RuntimeException("Telegram send error", e);
        }
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.TELEGRAM;
    }
}
