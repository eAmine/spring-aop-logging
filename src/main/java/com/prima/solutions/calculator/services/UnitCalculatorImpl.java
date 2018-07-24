package com.prima.solutions.calculator.services;

import com.prima.solutions.calculator.interfaces.UnitCalculator;
import com.prima.solutions.logging.Loggable;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

@Component("unitCalculator")
@Loggable
public class UnitCalculatorImpl implements UnitCalculator {

    @Override
    @Loggable(value = LogLevel.WARN, name = "your-logger-name")
    public double kilogramToPound(double kilogram) {
        return kilogram * 2.2;
    }

    @Override
    public double kilometerToMile(double kilometer) {
        return kilometer * 0.62;
    }
}
