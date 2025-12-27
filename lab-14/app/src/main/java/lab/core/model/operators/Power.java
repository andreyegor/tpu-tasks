package lab.core.model.operators;

public class Power extends BinaryOperation {
    @Override
    public double execute(double... operands) {
        return Math.pow(operands[0], operands[1]);
    }
    
    @Override
    public int getPriority() {
        return 3;
    }
    
    @Override
    public char getSymbol() {
        return '^';
    }
}
