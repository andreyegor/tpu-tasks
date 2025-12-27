package lab.calc.impl;

import lab.calc.model.Token;
import lab.calc.model.operators.Operation;
import lab.calc.model.operators.OperationFactory;

import java.util.*;

class CalculatorImpl {
    
    public static double calculate(List<Token> tokens) throws ArithmeticException {
        ExpressionValidatorImpl.validate(tokens);
        
        if (tokens.isEmpty()) {
            throw new ArithmeticException("Empty expression");
        }
        
        List<Token> rpn = toRPN(tokens);
        return evaluateRPN(rpn);
    }
    
    private static List<Token> toRPN(List<Token> tokens) {
        List<Token> output = new ArrayList<>();
        Stack<Token> operators = new Stack<>();
        
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            
            switch (token.type()) {
                case NUMBER:
                    output.add(token);
                    break;
                    
                case OPERATOR:
                    while (!operators.isEmpty()) {
                        Token top = operators.peek();
                        if (top.type() == Token.TokenType.OPEN_PAREN) {
                            break;
                        }
                        
                        Operation topOp = OperationFactory.create(top.value().charAt(0));
                        Operation currentOp = OperationFactory.create(token.value().charAt(0));
                        
                        if (topOp.getPriority() > currentOp.getPriority() ||
                            (topOp.getPriority() == currentOp.getPriority() && 
                             currentOp.getSymbol() != '^' && !topOp.isUnary())) {
                            output.add(operators.pop());
                        } else {
                            break;
                        }
                    }
                    operators.push(token);
                    break;
                    
                case OPEN_PAREN:
                    operators.push(token);
                    break;
                    
                case CLOSE_PAREN:
                    while (!operators.isEmpty() && operators.peek().type() != Token.TokenType.OPEN_PAREN) {
                        output.add(operators.pop());
                    }
                    if (operators.isEmpty()) {
                        throw new ArithmeticException("Brackets mismatch");
                    }
                    operators.pop();
                    break;
            }
        }
        
        while (!operators.isEmpty()) {
            Token op = operators.pop();
            if (op.type() == Token.TokenType.OPEN_PAREN) {
                throw new ArithmeticException("Brackets mismatch");
            }
            output.add(op);
        }
        
        return output;
    }
    
    private static double evaluateRPN(List<Token> rpn) throws ArithmeticException {
        Stack<Double> stack = new Stack<>();
        
        for (Token token : rpn) {
            if (token.type() == Token.TokenType.NUMBER) {
                stack.push(Double.parseDouble(token.value()));
            } else if (token.type() == Token.TokenType.OPERATOR) {
                Operation op = OperationFactory.create(token.value().charAt(0));
                
                if (op.getOperandCount() > stack.size()) {
                    throw new ArithmeticException("Not enough operands: " + token.value());
                }
                
                double[] operands = new double[op.getOperandCount()];
                for (int i = op.getOperandCount() - 1; i >= 0; i--) {
                    operands[i] = stack.pop();
                }
                
                double result = op.execute(operands);
                stack.push(result);
            }
        }
        
        if (stack.size() != 1) {
            throw new ArithmeticException("Invalid expression");
        }
        
        return stack.pop();
    }
}
