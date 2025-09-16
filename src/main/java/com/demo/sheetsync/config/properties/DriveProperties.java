package com.demo.sheetsync.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "drive")
public record DriveProperties(
        String webhookUrl
) {}
