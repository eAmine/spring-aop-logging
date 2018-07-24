package com.prima.solutions;

import com.prima.solutions.calculator.interfaces.ArithmeticCalculator;
import com.prima.solutions.calculator.interfaces.UnitCalculator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class LoggingApplication {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(LoggingApplication.class, args);
        ArithmeticCalculator arithmeticCalculator =
                app.getBean("arithmeticCalculator", ArithmeticCalculator.class);
        arithmeticCalculator.add(1, 2);
        arithmeticCalculator.sub(4, 3);
        arithmeticCalculator.mul(2, 3);
        arithmeticCalculator.div(4, 2);
        UnitCalculator unitCalculator = app.getBean("unitCalculator", UnitCalculator.class);
        unitCalculator.kilogramToPound(10);
        unitCalculator.kilometerToMile(5);
    }
}
