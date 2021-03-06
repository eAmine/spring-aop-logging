package com.prima.solutions.logging;

import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class Logger {

    public static final String LOG_LEVEL_MUST_NOT_BE_NULL = "LogLevel must not be null.";
    public static final String LOG_LEVEL_MUST_BE_ONE_OF_THE_ENABLED_LEVELS = "LogLevel must be one of the enabled levels.";

    private void log(org.slf4j.Logger logger, LogLevel level, String message) {
        Objects.requireNonNull(level, LOG_LEVEL_MUST_NOT_BE_NULL);
        switch (level) {
            case TRACE:
                logger.trace(message);
                break;
            case DEBUG:
                logger.debug(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
            case FATAL:
                logger.error(message);
                break;
            default:
                break;
        }
    }

    private void log(org.slf4j.Logger logger, LogLevel level, String message, Throwable err) {
        Objects.requireNonNull(level, LOG_LEVEL_MUST_NOT_BE_NULL);
        switch (level) {
            case TRACE:
                logger.trace(message, err);
                break;
            case DEBUG:
                logger.debug(message, err);
                break;
            case INFO:
                logger.info(message, err);
                break;
            case WARN:
                logger.warn(message, err);
                break;
            case ERROR:
            case FATAL:
                logger.error(message, err);
                break;
            default:
                break;
        }
    }

    public void log(LogLevel level, Class<?> clazz, String message) {
        log(LoggerFactory.getLogger(clazz), level, message);
    }

    public void log(LogLevel level, String name, String message) {
        log(LoggerFactory.getLogger(name), level, message);
    }

    public void log(Class<?> clazz, String message, Throwable err) {
        log(LoggerFactory.getLogger(clazz), LogLevel.ERROR, message, err);
    }

    public void log(LogLevel level, String name, String message, Throwable err) {
        log(LoggerFactory.getLogger(name), level, message, err);
    }

    private boolean isEnabled(org.slf4j.Logger logger, LogLevel level) {
        Objects.requireNonNull(level, LOG_LEVEL_MUST_NOT_BE_NULL);
        switch (level) {
            case TRACE:
                return logger.isTraceEnabled();
            case DEBUG:
                return logger.isDebugEnabled();
            case INFO:
                return logger.isInfoEnabled();
            case WARN:
                return logger.isWarnEnabled();
            case ERROR:
            case FATAL:
                return logger.isErrorEnabled();
            default:
                throw new IllegalArgumentException(LOG_LEVEL_MUST_BE_ONE_OF_THE_ENABLED_LEVELS);
        }
    }

    public boolean isEnabled(LogLevel level, Class<?> clazz) {
        return isEnabled(LoggerFactory.getLogger(clazz), level);
    }

    public boolean isEnabled(LogLevel level, String name) {
        return isEnabled(LoggerFactory.getLogger(name), level);
    }
}