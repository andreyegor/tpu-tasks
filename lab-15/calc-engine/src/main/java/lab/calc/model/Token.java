package lab.calc.model;

public record Token(TokenType type, String value) {
    public enum TokenType {
        NUMBER,
        OPERATOR,
        OPEN_PAREN,
        CLOSE_PAREN
    }
    
    @Override
    public String toString() {
        return value;
    }
}
