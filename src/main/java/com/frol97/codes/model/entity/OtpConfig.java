package com.frol97.codes.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "otp_conf")
@Getter
@Setter
public class OtpConfig {
    @Id
    private Integer id = 0;

    @Column(nullable = false)
    private Integer lifetime = 20;

    @Column(nullable = false)
    private Integer length = 4;
}
