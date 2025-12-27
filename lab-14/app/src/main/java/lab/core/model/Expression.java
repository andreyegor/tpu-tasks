package lab.core.model;

import java.util.List;
import java.util.ArrayList;

public class Expression {
    private final List<Token> tokens;
    
    public Expression(List<Token> tokens) {
        this.tokens = new ArrayList<>(tokens);
    }
    
    public Expression() {
        this.tokens = new ArrayList<>();
    }
    
    public List<Token> getTokens() {
        return new ArrayList<>(tokens);
    }
    
    public void addToken(Token token) {
        tokens.add(token);
    }
    
    public void removeLastToken() {
        if (!tokens.isEmpty()) {
            tokens.remove(tokens.size() - 1);
        }
    }
    
    public void clear() {
        tokens.clear();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Token token : tokens) {
            sb.append(token.value());
        }
        return sb.toString();
    }
    
    public boolean isEmpty() {
        return tokens.isEmpty();
    }
}
