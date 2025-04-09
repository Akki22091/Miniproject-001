package com.npst.miniproject.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "plan-api")
@EnableConfigurationProperties
@Data
public class ApplicationProperties {

    Map<String, String> messages = new HashMap<>();
}
