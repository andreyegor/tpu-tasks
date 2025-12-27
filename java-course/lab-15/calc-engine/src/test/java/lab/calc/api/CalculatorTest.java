package lab.calc.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Calculator")
class CalculatorTest {
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = CalculatorFactory.createCalculator();
    }
    
    @Test
    @DisplayName("Should calculate simple addition")
    void testSimpleAddition() {
        double result = calculator.calculate("2+3");
        assertEquals(5, result);
    }
    
    @Test
    @DisplayName("Should calculate subtraction")
    void testSubtraction() {
        double result = calculator.calculate("5-2");
        assertEquals(3, result);
    }
    
    @Test
    @DisplayName("Should calculate multiplication")
    void testMultiplication() {
        double result = calculator.calculate("4*3");
        assertEquals(12, result);
    }
    
    @Test
    @DisplayName("Should calculate power")
    void testPower() {
        double result = calculator.calculate("2^3");
        assertEquals(8, result);
    }
    
    @Test
    @DisplayName("Should calculate square root")
    void testSquareRoot() {
        double result = calculator.calculate("√16");
        assertEquals(4, result);
    }
    
    @Test
    @DisplayName("Should respect operator precedence")
    void testOperatorPrecedence() {
        double result = calculator.calculate("2+3*4");
        assertEquals(14, result);  // 2 + (3*4) = 14, not (2+3)*4 = 20
    }
    
    @Test
    @DisplayName("Should handle parentheses")
    void testParentheses() {
        double result = calculator.calculate("(2+3)*4");
        assertEquals(20, result);
    }
    
    @Test
    @DisplayName("Should handle complex expression")
    void testComplexExpression() {
        double result = calculator.calculate("((2+3)*4)^2");
        assertEquals(400, result);  // (5*4)^2 = 20^2 = 400
    }
    
    @Test
    @DisplayName("Should handle decimal numbers")
    void testDecimalNumbers() {
        double result = calculator.calculate("2.5+3.5");
        assertEquals(6, result);
    }
    
    @Test
    @DisplayName("Should throw exception for negative square root")
    void testNegativeSquareRoot() {
        assertThrows(Exception.class, () -> {
            calculator.calculate("√(-4)");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for empty expression")
    void testEmptyExpression() {
        assertThrows(ArithmeticException.class, () -> {
            calculator.calculate("");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for mismatched parentheses")
    void testMismatchedParentheses() {
        assertThrows(Exception.class, () -> {
            calculator.calculate("(2+3");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for mismatched opening parentheses")
    void testMismatchedOpeningParentheses() {
        assertThrows(Exception.class, () -> {
            calculator.calculate(")2+3(");
        });
    }
    
    @Test
    @DisplayName("Should calculate simple negation")
    void testSimpleNegation() {
        double result = calculator.calculate("0-5");
        assertEquals(-5, result);
    }
    
    @Test
    @DisplayName("Should handle division-like operations with power")
    void testPowerWithDecimal() {
        double result = calculator.calculate("4^0.5");
        assertEquals(2, result);
    }
    
    @Test
    @DisplayName("Should calculate expression with multiple operations")
    void testMultipleOperations() {
        double result = calculator.calculate("10-2*3+4");
        assertEquals(8, result);
    }
}
