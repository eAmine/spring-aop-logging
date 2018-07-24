package com.prima.solutions.logging;

/**
 * <p>
 * Logger message formats that can be replaced with the defaults.
 * </p>
 * There are list of placeholder's that can be used:
 * <ul>
 * <li>${method.name} - method name</li>
 * <li>${method.args} - method arguments</li>
 * <li>${method.result} - method results</li>
 * <li>${method.duration} - method runtime duration</li>
 * <li>${method.warn.duration} - method runtime warning duration to be displayed</li>
 * <li>${error.class.name} - exception class name</li>
 * <li>${error.message} - exception message</li>
 * <li>${error.source.class.name} - exception source class name</li>
 * <li>${error.source.line} - exception source line number that cause the exception</li>
 * </ul>
 */

public class LoggerFormats {

    private String enter;
    private String warnBefore;
    private String warnAfter;
    private String after;
    private String error;

    public LoggerFormats() {
    }

    public LoggerFormats(String enter, String warnBefore, String warnAfter, String after, String error) {
        this.enter = enter;
        this.warnBefore = warnBefore;
        this.warnAfter = warnAfter;
        this.after = after;
        this.error = error;
    }

    public String getEnter() {
        return enter;
    }

    public void setEnter(String enter) {
        this.enter = enter;
    }

    public String getWarnBefore() {
        return warnBefore;
    }

    public void setWarnBefore(String warnBefore) {
        this.warnBefore = warnBefore;
    }

    public String getWarnAfter() {
        return warnAfter;
    }

    public void setWarnAfter(String warnAfter) {
        this.warnAfter = warnAfter;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
