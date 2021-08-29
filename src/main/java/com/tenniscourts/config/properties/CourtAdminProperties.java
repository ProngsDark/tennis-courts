package com.tenniscourts.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "court-admin")
@Data
public class CourtAdminProperties {
    private int deposit;
}
