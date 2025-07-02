package com.frol97.codes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false, name = "operation_id")
    private String operationId;

    @Column(nullable = false)
    private Integer value;

    @Column(nullable = false)
    private OtpState state;

    @ManyToOne
    @JoinColumn(name = "user_login")
    @JsonIgnore
    private User user;

    @Column(nullable = false, name = "expires_at")
    @JsonProperty("expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "used_at")
    @JsonProperty("used_at")
    private LocalDateTime usedAt;

    public Otp() {}

    public Otp(String operationId, Integer value, OtpState state, User user, LocalDateTime expiresAt) {
        this.operationId = operationId;
        this.value = value;
        this.state = state;
        this.user = user;
        this.expiresAt = expiresAt;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public OtpState getState() {
        return state;
    }

    public void setState(OtpState state) {
        this.state = state;
    }
}