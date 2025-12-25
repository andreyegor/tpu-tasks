package lab.calc.model.operators;

public interface Operation {
    double execute(double... operands) throws ArithmeticException;
    
    int getPriority();
    
    int getOperandCount();
    
    boolean isUnary();
    
    char getSymbol();
}
