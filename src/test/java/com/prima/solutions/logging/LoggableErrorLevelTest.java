package com.prima.solutions.logging;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LoggableErrorLevelTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Autowired
    private Service service;

    @BeforeClass
    public static void setErrorLogging() {
        LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel(Logger.ROOT_LOGGER_NAME, LogLevel.ERROR);
    }

    @Test
    public void warn2SecWarnDisabledTest() {
        service.withWarn2Sec();
        Pattern pattern = Pattern
                .compile("(WARN com\\.prima\\.solutions\\.logging\\.LoggableErrorLevelTest\\$service "
                        + "- \\#withWarn2Sec\\(\\[\\]\\): in .* and still running \\(max PT0.002S\\))", Pattern.DOTALL);
        assertFalse(pattern.matcher(capture.toString()).find());
    }

    @Test
    public void notTraceTest() {
        service.withTrace();
        assertThat(capture.toString(), not(containsString(
                "TRACE com.prima.solutions.logging.LoggableErrorLevelTest$Service - "
                        + "#withTrace([]): NULL in")));
    }

    @Test
    public void debugTest() {
        service.withDebug();
        assertThat(capture.toString(), not(containsString(
                "DEBUG com.prima.solutions.logging.LoggableErrorLevelTest$Service - "
                        + "#withDebug([]): NULL in")));
    }

    @Test
    public void infoTest() {
        service.withInfo();
        assertThat(capture.toString(), not(containsString(
                "INFO com.prima.solutions.logging.LoggableErrorLevelTest$Service - "
                        + "#withDebug([]): NULL in")));
    }

    @Test
    public void errorTest() {
        service.withError();
        assertThat(capture.toString(), containsString(
                "ERROR com.prima.solutions.logging.LoggableErrorLevelTest$Service - "
                        + "#withError([]): NULL in"));
    }

    @Test
    public void offTest() {
        service.withOff();
        assertThat(capture.toString(), not(containsString(
                "com.prima.solutions.logging.LoggableErrorLevelTest$Service - "
                        + "#withOff([]): NULL in")));
    }

    @Test
    public void fatalTest() {
        service.withFatal();
        assertThat(capture.toString(), containsString(
                "ERROR com.prima.solutions.logging.LoggableErrorLevelTest$Service - "
                        + "#withFatal([]): NULL in"));
    }

    public static class Service {

        @Loggable(LogLevel.FATAL)
        public void withFatal() {

        }

        @Loggable(LogLevel.DEBUG)
        public void withDebug() {

        }

        @Loggable(LogLevel.INFO)
        public void withInfo() {

        }

        @Loggable(LogLevel.TRACE)
        public void withTrace() {

        }

        @Loggable(LogLevel.ERROR)
        public void withError() {

        }

        @Loggable(LogLevel.OFF)
        public void withOff() {

        }

        @Loggable(warnOver = 1, warnUnit = TimeUnit.NANOSECONDS)
        public void withWarn2Sec() {
            int number = 0;
            number++;
            assertEquals(1, number);
        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    @EnableLogger
    public static class Application {
        @Bean
        public Service service() {
            return new Service();
        }

    }

}
