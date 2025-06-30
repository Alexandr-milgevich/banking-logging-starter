package com.example.logging.properties;

import com.example.logging.constants.LevelLogging;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "banking.logging")
public class LoggingProperties {
    private boolean enabled = true;
    private boolean logArguments = true;
    private boolean logExecutionTime = true;

    private LevelLogging level = LevelLogging.INFO;
}