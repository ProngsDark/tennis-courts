package com.tenniscourts.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

@Configuration
@ConfigurationProperties(prefix = "pagination")
@Data
public class PaginationProperties {
    private int size;

    private int page;

    private Sort.Direction direction;
}
