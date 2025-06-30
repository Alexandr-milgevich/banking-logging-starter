package com.example.logging.config;

import com.example.logging.aspect.LoggingAspect;
import com.example.logging.properties.LoggingProperties;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(prefix = "banking.logging", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(Aspect.class)
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LoggingAspect loggingAspect(LoggingProperties properties) {
        return new LoggingAspect(properties);
    }
}