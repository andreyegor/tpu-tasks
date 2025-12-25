package lab.calc.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Expression model")
class ExpressionTest {
    
    @Test
    @DisplayName("Should create empty expression")
    void testCreateEmptyExpression() {
        Expression expr = new Expression();
        assertTrue(expr.isEmpty());
        assertEquals(0, expr.getTokens().size());
    }
    
    @Test
    @DisplayName("Should add token to expression")
    void testAddToken() {
        Expression expr = new Expression();
        Token token = new Token(Token.TokenType.NUMBER, "42");
        
        expr.addToken(token);
        
        assertFalse(expr.isEmpty());
        assertEquals(1, expr.getTokens().size());
        assertEquals("42", expr.getTokens().get(0).value());
    }
    
    @Test
    @DisplayName("Should remove last token")
    void testRemoveLastToken() {
        Expression expr = new Expression();
        expr.addToken(new Token(Token.TokenType.NUMBER, "42"));
        expr.addToken(new Token(Token.TokenType.OPERATOR, "+"));
        
        expr.removeLastToken();
        
        assertEquals(1, expr.getTokens().size());
        assertEquals("42", expr.getTokens().get(0).value());
    }
    
    @Test
    @DisplayName("Should clear expression")
    void testClear() {
        Expression expr = new Expression();
        expr.addToken(new Token(Token.TokenType.NUMBER, "42"));
        expr.addToken(new Token(Token.TokenType.OPERATOR, "+"));
        
        expr.clear();
        
        assertTrue(expr.isEmpty());
    }
    
    @Test
    @DisplayName("Should convert expression to string")
    void testToString() {
        Expression expr = new Expression();
        expr.addToken(new Token(Token.TokenType.NUMBER, "2"));
        expr.addToken(new Token(Token.TokenType.OPERATOR, "+"));
        expr.addToken(new Token(Token.TokenType.NUMBER, "3"));
        
        assertEquals("2+3", expr.toString());
    }
    
    @Test
    @DisplayName("Should create expression from token list")
    void testCreateFromTokenList() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(Token.TokenType.NUMBER, "5"));
        tokens.add(new Token(Token.TokenType.OPERATOR, "*"));
        tokens.add(new Token(Token.TokenType.NUMBER, "4"));
        
        Expression expr = new Expression(tokens);
        
        assertEquals(3, expr.getTokens().size());
        assertEquals("5*4", expr.toString());
    }
}
