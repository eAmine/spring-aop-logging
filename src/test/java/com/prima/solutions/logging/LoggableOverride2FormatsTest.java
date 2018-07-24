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
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class LoggableOverride2FormatsTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Autowired
    private SomeService3 someService3;

    @BeforeClass
    public static void setErrorLogging() {
        LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel(Logger.ROOT_LOGGER_NAME, LogLevel.INFO);
    }

    @Test
    public void overrideFormats() {
        someService3.defaultLog();
        assertThat(capture.toString(), containsString(
                "INFO com.prima.solutions.logging.LoggableOverride2FormatsTest$SomeService3 - "
                        + "override format defaultLog"));
    }

    public static class SomeService3 {

        @Loggable
        public void defaultLog() {

        }
    }

    @Configuration
    @EnableAspectJAutoProxy
    @EnableLogger
    public static class Application {
        @Bean
        public SomeService3 someService3() {
            return new SomeService3();
        }

        @Bean
        @Profile("override-formats")
        public LoggerFormats loggerFormats() {
            LoggerFormats format = new LoggerFormats();
            format.setEnter("");
            format.setAfter("override format ${method.name}");
            format.setWarnBefore("");
            format.setWarnAfter("");
            format.setError("");

            return format;
        }
    }

}