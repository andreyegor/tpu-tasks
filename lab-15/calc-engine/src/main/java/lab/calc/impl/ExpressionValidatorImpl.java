package lab.calc.impl;

import lab.calc.model.Token;

import java.util.List;

class ExpressionValidatorImpl {
    public static void validate(List<Token> tokens) throws IllegalArgumentException {
        if (tokens.isEmpty()) {
            return;
        }
        
        int parenCount = 0;
        Token prevToken = null;
        
        for (Token token : tokens) {
            switch (token.type()) {
                case OPEN_PAREN:
                    parenCount++;
                    if (prevToken != null && 
                        (prevToken.type() == Token.TokenType.NUMBER ||
                         prevToken.type() == Token.TokenType.CLOSE_PAREN)) {
                        throw new IllegalArgumentException("Error: missing operation");
                    }
                    break;
                    
                case CLOSE_PAREN:
                    parenCount--;
                    if (parenCount < 0) {
                        throw new IllegalArgumentException("Error: extra closing parenthesis");
                    }
                    if (prevToken != null && 
                        (prevToken.type() == Token.TokenType.OPERATOR ||
                         prevToken.type() == Token.TokenType.OPEN_PAREN)) {
                        throw new IllegalArgumentException("Error: incomplete operation");
                    }
                    break;
                    
                case OPERATOR:
                    if (prevToken == null || 
                        prevToken.type() == Token.TokenType.OPERATOR ||
                        prevToken.type() == Token.TokenType.OPEN_PAREN) {
                        if (!isUnaryOperator(token.value())) {
                            throw new IllegalArgumentException("Error: operator " + token.value() + " requires left operand");
                        }
                    }
                    break;
                    
                case NUMBER:
                    if (prevToken != null && 
                        (prevToken.type() == Token.TokenType.NUMBER ||
                         prevToken.type() == Token.TokenType.CLOSE_PAREN)) {
                        throw new IllegalArgumentException("Error: missing operation between numbers");
                    }
                    break;
            }
            prevToken = token;
        }
        
        if (parenCount != 0) {
            throw new IllegalArgumentException("Error: parenthesis mismatch");
        }
        
        if (prevToken != null && 
            (prevToken.type() == Token.TokenType.OPERATOR ||
             prevToken.type() == Token.TokenType.OPEN_PAREN)) {
            throw new IllegalArgumentException("Error: incomplete expression");
        }
    }
    
    private static boolean isUnaryOperator(String op) {
        return op.equals("√");
    }
}
