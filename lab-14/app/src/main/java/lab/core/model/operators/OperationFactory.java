package lab.core.model.operators;

public class OperationFactory {
    public static Operation create(char symbol) {
        return switch (symbol) {
            case '+' -> new Addition();
            case '-' -> new Subtraction();
            case '*' -> new Multiplication();
            case '^' -> new Power();
            case '√' -> new SquareRoot();
            default -> throw new IllegalArgumentException("Неизвестная операция: " + symbol);
        };
    }
}
