package lab.calc.impl;

import lab.calc.api.ExpressionParser;
import lab.calc.model.Token;

import java.util.List;

public class DefaultExpressionParser implements ExpressionParser {
    @Override
    public List<Token> parse(String expression) throws IllegalArgumentException {
        return new TokenizerImpl(expression).tokenize();
    }
}
