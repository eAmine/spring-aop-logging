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
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class LoggableTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Autowired
    private SomeService someService;

    @Autowired
    private SomeClassService someClassService;

    @BeforeClass
    public static void setErrorLogging() {
        LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel(Logger.ROOT_LOGGER_NAME, LogLevel.DEBUG);
    }

    @Test
    public void defaultTest() {
        someService.withDefault();
        assertThat(capture.toString(), containsString(
                "INFO com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withDefault([]): NULL in"));
    }

    @Test
    public void defaultSkipArgsAndResultTest() {
        someService.withSkipArgsAndResultDefault(0);
        assertThat(capture.toString(), containsString(
                "INFO com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withSkipArgsAndResultDefault(..): .. in"));
    }

    @Test
    public void paramsTest() {
        someService.withParams("str", 10);
        assertThat(capture.toString(), containsString(
                "INFO com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withParams(['str', 10]): NULL in"));
    }

    @Test
    public void paramsArrayBoolTest() {
        someService.withParamsArrayBool(new boolean[]{true, false, true});
        assertThat(capture.toString(), containsString(
                "INFO com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withParamsArrayBool([[true, false, true]]): NULL in"));
    }

    @Test
    public void paramsArrayFloatTest() {
        someService.withParamsArrayFloat(new float[]{1.2f, 3.4f, 5.6f});
        assertThat(capture.toString(), containsString(
                "INFO com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withParamsArrayFloat([[1.2, 3.4, 5.6]]): NULL in"));
    }

    @Test
    public void paramsArrayTest() {
        someService.withParamsArray(new int[]{1, 2, 3});
        assertThat(capture.toString(), containsString(
                "INFO com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withParamsArray([[1, 2, 3]]): NULL in"));
    }

    @Test
    public void paramsReturnTest() {
        someService.withParamsReturn("str", 10);
        assertThat(capture.toString(), containsString(
                "INFO com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withParamsReturn(['str', 10]): 10 in"));
    }

    @Test
    public void notTraceTest() {
        someService.withTrace();
        assertThat(capture.toString(), not(containsString(
                "TRACE com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withTrace([]): NULL in")));
    }

    @Test
    public void debugTest() {
        someService.withDebug();
        assertThat(capture.toString(), containsString(
                "DEBUG com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withDebug([]): NULL in"));
    }

    @Test
    public void errorTest() {
        someService.withError();
        assertThat(capture.toString(), containsString(
                "ERROR com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withError([]): NULL in"));
    }

    @Test
    public void offTest() {
        someService.withOff();
        assertThat(capture.toString(), not(containsString(
                "com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withOff([]): NULL in")));
    }

    @Test
    public void fatalTest() {
        someService.withFatal();
        assertThat(capture.toString(), containsString(
                "ERROR com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withFatal([]): NULL in"));
    }

    @Test
    public void enterTest() {
        someService.withEnter();
        assertThat(capture.toString(), containsString(
                "INFO com.prima.solutions.logging.LoggableTest$SomeService - "
                        + "#withEnter([]): entered"));
    }

    @Test
    public void throwTest() {
        try {
            someService.withThrow();
        } catch (Exception ignore) {
        }
        Pattern pattern = Pattern.compile(
                "(ERROR com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService - "
                        + "#withThrow\\(\\[]\\): thrown java\\.lang\\.Exception\\(withThrow\\) "
                        + "from com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService\\[[0-9]*] "
                        + "in .*java\\.lang\\.Exception: withThrow.*at com\\.prima\\.solutions\\.logging)",
                Pattern.DOTALL | Pattern.MULTILINE);
        assertTrue(pattern.matcher(capture.toString()).find());
    }

    @Test
    public void throwChildIgnoreTest() {
        try {
            someService.withThrowChildIgnore();
        } catch (Exception ignore) {
        }
        Pattern pattern = Pattern.compile(
                "(ERROR com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService - "
                        + "#withThrowChildIgnore\\(\\[]\\): thrown java\\.io\\.FileNotFoundException\\(withThrowChildIgnore\\) "
                        + "from com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService\\[[0-9]*] "
                        + "in .*java\\.lang\\.Exception: withThrowChildIgnore.*at com\\.prima\\.solutions\\.logging)",
                Pattern.DOTALL | Pattern.MULTILINE);
        assertFalse(pattern.matcher(capture.toString()).find());
        Pattern pattern2 = Pattern.compile(
                "(ERROR com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService - "
                        + "#withThrowChildIgnore\\(\\[]\\): thrown java\\.io\\.FileNotFoundException\\(withThrowChildIgnore\\) "
                        + "from com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService\\[[0-9]*] in)",
                Pattern.DOTALL);
        assertTrue(pattern2.matcher(capture.toString()).find());
    }

    @Test
    public void throwIgnoreTest() {
        try {
            someService.withThrowIgnore();
        } catch (Exception ignore) {
        }
        Pattern pattern = Pattern.compile(
                "(ERROR com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService - "
                        + "#withThrowIgnore\\(\\[]\\): thrown java\\.lang\\.Exception\\(withThrowIgnore\\) "
                        + "from com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService\\[[0-9]*] "
                        + "in .*java\\.lang\\.Exception: withThrowIgnore.*at com\\.prima\\.solutions\\.logging)",
                Pattern.DOTALL | Pattern.MULTILINE);
        assertFalse(pattern.matcher(capture.toString()).find());
        Pattern pattern2 = Pattern.compile(
                "(ERROR com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService - "
                        + "#withThrowIgnore\\(\\[]\\): thrown java\\.lang\\.Exception\\(withThrowIgnore\\) "
                        + "from com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService\\[[0-9]*] in)",
                Pattern.DOTALL);
        assertTrue(pattern2.matcher(capture.toString()).find());
    }

    @Test
    public void throwChildIgnoreNotFoundTest() {
        try {
            someService.withThrowChildNotFoundIgnore();
        } catch (Exception ignore) {
        }
        Pattern pattern = Pattern.compile(
                "(ERROR com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService - "
                        + "#withThrowChildNotFoundIgnore\\(\\[]\\): "
                        + "thrown java\\.io\\.FileNotFoundException\\(withThrowChildNotFoundIgnore\\) "
                        + "from com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService\\[[0-9]*] "
                        + "in .*java\\.io\\.FileNotFoundException: "
                        + "withThrowChildNotFoundIgnore.*at com\\.prima\\.solutions\\.logging)",
                Pattern.DOTALL | Pattern.MULTILINE);
        assertTrue(pattern.matcher(capture.toString()).find());
    }

    @Test
    public void warn2SecTest() {
        someService.withWarn2Sec();
        Pattern pattern = Pattern.compile(
                "(WARN com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService "
                        + "- #withWarn2Sec\\(\\[]\\): NULL in .* \\(max PT0S\\))",
                Pattern.DOTALL);
        assertTrue(pattern.matcher(capture.toString()).find());
    }

    @Test
    public void defaultClassTest() {
        someClassService.withClassDefault();
        assertThat(capture.toString(), containsString(
                "INFO com.prima.solutions.logging.LoggableTest$SomeClassService - "
                        + "#withClassDefault([]): NULL in "));
    }

    @Test
    public void namedDefaultTest() {
        someService.withName();
        assertThat(capture.toString(), containsString(
                "INFO someServiceModel - "
                        + "#withName([]): NULL in"));
    }

    @Test
    public void throwNameIgnoreTest() {
        try {
            someService.withNameThrow();
        } catch (Exception ignore) {
        }
        Pattern pattern = Pattern.compile("(ERROR someServiceModelWithThrow - "
                        + "#withNameThrow\\(\\[]\\): thrown java\\.lang\\.Exception\\(withNameThrow\\) "
                        + "from com\\.prima\\.solutions\\.logging\\.LoggableTest\\$SomeService\\[[0-9]*] "
                        + "in .*java\\.lang\\.Exception: withNameThrow.*at com\\.prima\\.solutions\\.logging)",
                Pattern.DOTALL | Pattern.MULTILINE);
        assertTrue(pattern.matcher(capture.toString()).find());
    }

    @Loggable
    public static class SomeClassService {
        public void withClassDefault() {

        }
    }

    public static class SomeService {

        @Loggable(name = "someServiceModel")
        public void withName() {

        }

        @Loggable(name = "someServiceModelWithThrow")
        public void withNameThrow() throws Exception {
            throw new Exception("withNameThrow");
        }

        @Loggable
        public void withDefault() {

        }

        @Loggable(skipArgs = true, skipResult = true)
        public int withSkipArgsAndResultDefault(int num) {
            return 2;
        }

        @Loggable
        public void withParamsArrayBool(boolean[] bools) {

        }

        @Loggable
        public void withParamsArrayFloat(float[] floats) {

        }

        @Loggable
        public void withParams(String str, int num) {

        }

        @Loggable
        public void withParamsArray(int[] nums) {

        }

        @Loggable
        public int withParamsReturn(String str, int num) {
            return num;
        }

        @Loggable(LogLevel.FATAL)
        public void withFatal() {

        }

        @Loggable(LogLevel.DEBUG)
        public void withDebug() {

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

        @Loggable(entered = true)
        public void withEnter() {

        }

        @Loggable
        public void withThrow() throws Exception {
            throw new Exception("withThrow");
        }

        @Loggable(ignore = IOException.class)
        public void withThrowChildIgnore() throws Exception {
            throw new FileNotFoundException("withThrowChildIgnore");
        }

        @Loggable(ignore = Exception.class)
        public void withThrowIgnore() throws Exception {
            throw new Exception("withThrowIgnore");
        }

        @Loggable(ignore = RuntimeException.class)
        public void withThrowChildNotFoundIgnore() throws Exception {
            throw new FileNotFoundException("withThrowChildNotFoundIgnore");
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
        public SomeService someService() {
            return new SomeService();
        }

        @Bean
        public SomeClassService someClassService() {
            return new SomeClassService();
        }
    }

}
