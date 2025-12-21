package lab.core.model.operators;

public abstract class BinaryOperation implements Operation {
    @Override
    public int getOperandCount() {
        return 2;
    }
    
    @Override
    public boolean isUnary() {
        return false;
    }
}
