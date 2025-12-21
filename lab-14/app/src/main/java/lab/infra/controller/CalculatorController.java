package lab.infra.controller;

import lab.core.logic.Calculator;
import lab.core.logic.ExpressionValidator;
import lab.core.model.Expression;
import lab.core.model.Token;

import java.util.*;

public class CalculatorController {
    private final Expression expression;
    private final List<CalculatorObserver> observers;
    private boolean resultCalculated = false;
    
    public CalculatorController() {
        this.expression = new Expression();
        this.observers = new ArrayList<>();
    }
    
    public void addDigit(String digit) {
        if (resultCalculated) {
            expression.clear();
            resultCalculated = false;
        }
        
        List<Token> tokens = expression.getTokens();
        
        if (!tokens.isEmpty() && tokens.get(tokens.size() - 1).type() == Token.TokenType.NUMBER) {
            expression.removeLastToken();
            Token lastToken = tokens.get(tokens.size() - 1);
            String newValue = lastToken.value() + digit;
            expression.addToken(new Token(Token.TokenType.NUMBER, newValue));
        } else {
            expression.addToken(new Token(Token.TokenType.NUMBER, digit));
        }
        
        notifyObservers();
    }
    
    public void addOperator(String operator) {
        List<Token> tokens = expression.getTokens();
        
        if (tokens.isEmpty()) {
            return;
        }
        
        if ("√".equals(operator)) {
            expression.addToken(new Token(Token.TokenType.OPERATOR, operator));
            notifyObservers();
            return;
        }
        
        Token lastToken = tokens.get(tokens.size() - 1);
        if (lastToken.type() == Token.TokenType.NUMBER || lastToken.type() == Token.TokenType.CLOSE_PAREN) {
            expression.addToken(new Token(Token.TokenType.OPERATOR, operator));
            notifyObservers();
        }
    }
    
    public void addOpenParen() {
        List<Token> tokens = expression.getTokens();
        
        if (tokens.isEmpty() || 
            tokens.get(tokens.size() - 1).type() == Token.TokenType.OPERATOR ||
            tokens.get(tokens.size() - 1).type() == Token.TokenType.OPEN_PAREN) {
            expression.addToken(new Token(Token.TokenType.OPEN_PAREN, "("));
            notifyObservers();
        }
    }
    
    public void addCloseParen() {
        List<Token> tokens = expression.getTokens();
        
        if (!tokens.isEmpty() && 
            (tokens.get(tokens.size() - 1).type() == Token.TokenType.NUMBER ||
             tokens.get(tokens.size() - 1).type() == Token.TokenType.CLOSE_PAREN)) {
            expression.addToken(new Token(Token.TokenType.CLOSE_PAREN, ")"));
            notifyObservers();
        }
    }
    
    public void backspace() {
        expression.removeLastToken();
        notifyObservers();
    }
    
    public void clear() {
        expression.clear();
        notifyObservers();
    }
    
    public String getExpressionString() {
        return expression.toString();
    }
    
    public String getIntermediateResult() {
        try {
            List<Token> tokens = expression.getTokens();
            if (tokens.isEmpty()) {
                return "";
            }
            
            ExpressionValidator.validate(tokens);
            
            Token lastToken = tokens.get(tokens.size() - 1);
            if (lastToken.type() == Token.TokenType.NUMBER || 
                lastToken.type() == Token.TokenType.CLOSE_PAREN) {
                double result = Calculator.calculate(tokens);
                return formatResult(result);
            }
            
            return "";
        } catch (Exception e) {
            return "";
        }
    }
    
    public String calculate() {
        try {
            List<Token> tokens = expression.getTokens();
            ExpressionValidator.validate(tokens);
            
            double result = Calculator.calculate(tokens);
            String resultStr = formatResult(result);
            
            expression.clear();
            expression.addToken(new Token(Token.TokenType.NUMBER, resultStr));
            resultCalculated = true;
            notifyObservers();
            
            return resultStr;
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
    
    private String formatResult(double result) {
        if (result == Math.floor(result) && !Double.isInfinite(result)) {
            return String.format("%.0f", result);
        }
        return String.format("%.10g", result);
    }
    
    public void addObserver(CalculatorObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(CalculatorObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyObservers() {
        for (CalculatorObserver observer : observers) {
            observer.update(this);
        }
    }
    
    public interface CalculatorObserver {
        void update(CalculatorController controller);
    }
}
