package lab.calc.api;

import lab.calc.model.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for ExpressionParser")
class ExpressionParserTest {
    private ExpressionParser parser;
    
    @BeforeEach
    void setUp() {
        parser = CalculatorFactory.createExpressionParser();
    }
    
    @Test
    @DisplayName("Should parse simple number")
    void testParseSimpleNumber() {
        List<Token> tokens = parser.parse("42");
        
        assertEquals(1, tokens.size());
        assertEquals(Token.TokenType.NUMBER, tokens.get(0).type());
        assertEquals("42", tokens.get(0).value());
    }
    
    @Test
    @DisplayName("Should parse decimal number")
    void testParseDecimalNumber() {
        List<Token> tokens = parser.parse("3.14");
        
        assertEquals(1, tokens.size());
        assertEquals(Token.TokenType.NUMBER, tokens.get(0).type());
        assertEquals("3.14", tokens.get(0).value());
    }
    
    @Test
    @DisplayName("Should parse addition expression")
    void testParseAdditionExpression() {
        List<Token> tokens = parser.parse("2+3");
        
        assertEquals(3, tokens.size());
        assertEquals(Token.TokenType.NUMBER, tokens.get(0).type());
        assertEquals(Token.TokenType.OPERATOR, tokens.get(1).type());
        assertEquals("+", tokens.get(1).value());
        assertEquals(Token.TokenType.NUMBER, tokens.get(2).type());
    }
    
    @Test
    @DisplayName("Should parse expression with parentheses")
    void testParseExpressionWithParentheses() {
        List<Token> tokens = parser.parse("(2+3)*4");
        
        assertEquals(7, tokens.size());
        assertEquals(Token.TokenType.OPEN_PAREN, tokens.get(0).type());
    }
    
    @Test
    @DisplayName("Should parse square root operator")
    void testParseSquareRoot() {
        List<Token> tokens = parser.parse("√9");
        
        assertEquals(2, tokens.size());
        assertEquals(Token.TokenType.OPERATOR, tokens.get(0).type());
        assertEquals("√", tokens.get(0).value());
    }
    
    @Test
    @DisplayName("Should throw exception for unknown character")
    void testParseUnknownCharacter() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("2&3");
        });
    }
    
    @Test
    @DisplayName("Should handle whitespace")
    void testParseWithWhitespace() {
        List<Token> tokens = parser.parse("2 + 3");
        
        assertEquals(3, tokens.size());
    }
}
