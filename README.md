# Otpservice

Домашнее задание. Защита OTP-кодом от Promo IT

# Spring Boot приложение для управления пользователями и OTP-кодами с поддержкой многоканальной рассылки.

## Spring Boot 21 + микросервис, который  
*регистрирует* пользователей,  
*рассылает* им одноразовые коды через SMS / e-mail / Telegram / файл  
и *проверяет* эти коды по RFC 6238 (TOTP).
---

## TL;DR — как запустить локально

```bash
# 1. БД
docker run -d --name pg -e POSTGRES_PASSWORD=pg \
           -p 5432:5432 postgres:17-alpine

# 2. Приложение
./mvnw spring-boot:run

Минимальные требования
Софт	Версия
Java (JDK)	21+
PostgreSQL	17
Maven	3.9+
Docker (dev)	24+
```

### API методы
| Метод | Описание                        | Endpoint           |
|-------|---------------------------------|--------------------|
| POST  | Регистрация нового пользователя | /api/auth/sign-up |
| POST  | Аутентификация (JWT токен)      | /api/auth/sign-in |
| GET   | Получить конфигурацию OTP       | /api/admin/get-otp-config |
| POST  | Установить конфигурацию OTP     | /api/admin/set-otp-config |
| GET   | Получить список пользователей   | /api/admin/get-users |
| POST  | Удалить пользователя            | /api/admin/delete-user |
| POST  | Сгенерировать OTP               | /api/otp/generate |
| POST  | Проверить OTP                   | /api/otp/validate|


### Каналы доставки
| Канал | Что нужно                      | Где смотреть результат         |
|-------|---------------------------------|--------------------|
| SMS	  | ничего, эмулятор | logs/sms.log |
| E-mail  | настроить spring.mail.*     | logs/sms.log |
| Telegram   | bot.token       | сообщения в чате бота  |
| Файл   | включён по умолчанию  |otp_codes.txt |

