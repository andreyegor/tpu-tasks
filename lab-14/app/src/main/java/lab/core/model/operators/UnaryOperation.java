package lab.core.model.operators;

public abstract class UnaryOperation implements Operation {
    @Override
    public int getOperandCount() {
        return 1;
    }
    
    @Override
    public boolean isUnary() {
        return true;
    }
}
