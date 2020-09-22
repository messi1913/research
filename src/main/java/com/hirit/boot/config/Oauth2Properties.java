package com.hirit.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2Properties {

    private String resourceId;
    private int expiredPeriod;
    private String publicKey;
    private String clientId;
    private String clientSecret;

}
