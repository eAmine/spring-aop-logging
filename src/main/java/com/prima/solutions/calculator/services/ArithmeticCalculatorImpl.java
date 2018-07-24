package com.prima.solutions.calculator.services;

import com.prima.solutions.calculator.interfaces.ArithmeticCalculator;
import com.prima.solutions.logging.Loggable;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("arithmeticCalculator")
public class ArithmeticCalculatorImpl implements ArithmeticCalculator {

    @Override
    @Loggable(LogLevel.WARN)
    public double add(double a, double b) {
        return a + b;
    }

    @Override
    @Loggable(entered = true)
    public double sub(double a, double b) {
        return a - b;
    }

    @Override
    @Loggable(skipArgs = true, skipResult = true)
    public double mul(double a, double b) {
        return a * b;
    }

    @Override
    @Loggable(warnOver = 2, warnUnit = TimeUnit.NANOSECONDS)
    public double div(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero");
        }
        return a / b;
    }
}
