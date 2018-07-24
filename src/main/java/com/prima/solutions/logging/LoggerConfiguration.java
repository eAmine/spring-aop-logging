package com.prima.solutions.logging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the logger.
 *
 * @see EnableLogger
 */
@Configuration
@ComponentScan
public class LoggerConfiguration {

    private static final String METHOD_NAME_AND_ARGS = "#${method.name}(${method.args}): ";

    @Bean
    @ConditionalOnMissingBean(LoggerFormats.class)
    public LoggerFormats loggerFormats() {
        LoggerFormats loggerFormats = new LoggerFormats();
        loggerFormats.setEnter(METHOD_NAME_AND_ARGS
                + "entered");
        loggerFormats.setWarnBefore(METHOD_NAME_AND_ARGS
                + "in ${method.duration} and still running (max ${method.warn.duration})");
        loggerFormats.setWarnAfter(METHOD_NAME_AND_ARGS
                + "${method.result} in ${method.duration} (max ${method.warn.duration})");
        loggerFormats.setAfter(METHOD_NAME_AND_ARGS
                + "${method.result} in ${method.duration}");
        loggerFormats.setError(METHOD_NAME_AND_ARGS
                + "thrown ${error.class.name}(${error.message}) "
                + "from ${error.source.class.name}[${error.source.line}] in ${method.duration}");
        return loggerFormats;
    }
}