package lab.calc.impl;

import lab.calc.api.Calculator;
import lab.calc.api.ExpressionParser;
import lab.calc.model.Token;

import java.util.List;

public class DefaultCalculator implements Calculator {
    private final ExpressionParser parser = new DefaultExpressionParser();
    
    @Override
    public double calculate(List<Token> tokens) throws ArithmeticException {
        return CalculatorImpl.calculate(tokens);
    }
    
    @Override
    public double calculate(String expression) throws IllegalArgumentException, ArithmeticException {
        List<Token> tokens = parser.parse(expression);
        return calculate(tokens);
    }
}
