package lab.core.logic;

import java.util.ArrayList;
import java.util.List;

import lab.core.model.Token;

public class Tokenizer {
    private final String expression;
    private int index = 0;
    
    public Tokenizer(String expression) {
        this.expression = expression.trim();
    }
    
    public List<Token> tokenize() throws IllegalArgumentException {
        List<Token> tokens = new ArrayList<>();
        
        while (index < expression.length()) {
            skipWhitespace();
            
            if (index >= expression.length()) break;
            
            char ch = expression.charAt(index);
            
            if (Character.isDigit(ch) || ch == '.') {
                tokens.add(parseNumber());
            } else if (ch == '(') {
                tokens.add(new Token(Token.TokenType.OPEN_PAREN, "("));
                index++;
            } else if (ch == ')') {
                tokens.add(new Token(Token.TokenType.CLOSE_PAREN, ")"));
                index++;
            } else if (isOperator(ch)) {
                tokens.add(new Token(Token.TokenType.OPERATOR, String.valueOf(ch)));
                index++;
            } else if (ch == '√') {
                tokens.add(new Token(Token.TokenType.OPERATOR, "√"));
                index++;
            } else {
                throw new IllegalArgumentException("Unknown character: " + ch);
            }
        }
        
        return tokens;
    }
    
    private Token parseNumber() throws IllegalArgumentException {
        StringBuilder number = new StringBuilder();
        boolean hasDot = false;
        
        while (index < expression.length()) {
            char ch = expression.charAt(index);
            
            if (Character.isDigit(ch)) {
                number.append(ch);
                index++;
            } else if (ch == '.' && !hasDot) {
                hasDot = true;
                number.append(ch);
                index++;
            } else {
                break;
            }
        }
        
        if (number.isEmpty() || number.toString().equals(".")) {
            throw new IllegalArgumentException("Invalid number");
        }
        
        return new Token(Token.TokenType.NUMBER, number.toString());
    }
    
    private void skipWhitespace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }
    
    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '^';
    }
}
