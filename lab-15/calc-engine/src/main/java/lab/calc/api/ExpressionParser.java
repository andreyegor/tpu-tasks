package lab.calc.api;

import lab.calc.model.Token;
import java.util.List;

public interface ExpressionParser {
    List<Token> parse(String expression) throws IllegalArgumentException;
}
