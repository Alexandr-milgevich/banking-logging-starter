package com.example.logging.aspect;

import com.example.logging.constants.LevelLogging;
import com.example.logging.properties.LoggingProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {
    private final LoggingProperties properties;

    @Around("@within(org.springframework.stereotype.Service) || " +
            "@within(org.springframework.web.bind.annotation.RestController)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.isEnabled()) {
            return joinPoint.proceed();
        }

        String methodName = joinPoint.getSignature().toShortString();

        // Логирование входа
        log(properties.getLevel(), "Entering {} with args: {}", methodName,
                properties.isLogArguments() ? Arrays.toString(joinPoint.getArgs()) : "[arguments logging disabled]");

        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();

            // Логирование выхода
            if (properties.isLogExecutionTime()) {
                long executionTime = System.currentTimeMillis() - startTime;
                log(properties.getLevel(), "Exiting {}. Execution time: {} ms. Result: {}",
                        methodName, executionTime, result);
            } else {
                log(properties.getLevel(), "Exiting {}. Result: {}", methodName, result);
            }

            return result;
        } catch (Exception e) {
            log.error("Exception in {}: {}", methodName, e.getMessage(), e);
            throw e;
        }
    }

    private void log(LevelLogging level, String format, Object... arguments) {
        switch (level) {
            case TRACE -> log.trace(format, arguments);
            case DEBUG -> log.debug(format, arguments);
            case INFO -> log.info(format, arguments);
            case WARN -> log.warn(format, arguments);
            case ERROR -> log.error(format, arguments);
        }
    }
}