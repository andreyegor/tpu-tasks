package lab.calc.model.operators;

public class Subtraction extends BinaryOperation {
    @Override
    public double execute(double... operands) {
        return operands[0] - operands[1];
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
    
    @Override
    public char getSymbol() {
        return '-';
    }
}
