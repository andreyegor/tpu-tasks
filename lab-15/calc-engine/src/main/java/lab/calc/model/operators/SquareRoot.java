package lab.calc.model.operators;

public class SquareRoot extends UnaryOperation {
    @Override
    public double execute(double... operands) {
        if (operands[0] < 0) {
            throw new ArithmeticException("Корень из отрицательного числа");
        }
        return Math.sqrt(operands[0]);
    }
    
    @Override
    public int getPriority() {
        return 4;
    }
    
    @Override
    public char getSymbol() {
        return '√';
    }
}
