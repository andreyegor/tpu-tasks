package lab.calc.api;

import lab.calc.model.Token;
import java.util.List;

public interface Calculator {
    double calculate(List<Token> tokens) throws ArithmeticException;
    double calculate(String expression) throws IllegalArgumentException, ArithmeticException;
}
