package lab.calc.model.operators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Operations")
class OperationsTest {
    
    @Test
    @DisplayName("Should execute addition")
    void testAddition() {
        Operation add = new Addition();
        double result = add.execute(2, 3);
        assertEquals(5, result);
    }
    
    @Test
    @DisplayName("Should execute subtraction")
    void testSubtraction() {
        Operation sub = new Subtraction();
        double result = sub.execute(10, 3);
        assertEquals(7, result);
    }
    
    @Test
    @DisplayName("Should execute multiplication")
    void testMultiplication() {
        Operation mul = new Multiplication();
        double result = mul.execute(4, 5);
        assertEquals(20, result);
    }
    
    @Test
    @DisplayName("Should execute power operation")
    void testPower() {
        Operation pow = new Power();
        double result = pow.execute(2, 8);
        assertEquals(256, result);
    }
    
    @Test
    @DisplayName("Should execute square root")
    void testSquareRoot() {
        Operation sqrt = new SquareRoot();
        double result = sqrt.execute(9);
        assertEquals(3, result);
    }
    
    @Test
    @DisplayName("Should throw exception for negative square root")
    void testNegativeSquareRoot() {
        Operation sqrt = new SquareRoot();
        assertThrows(ArithmeticException.class, () -> {
            sqrt.execute(-4);
        });
    }
    
    @Test
    @DisplayName("Should return correct priority for addition")
    void testAdditionPriority() {
        Operation add = new Addition();
        assertEquals(1, add.getPriority());
    }
    
    @Test
    @DisplayName("Should return correct priority for multiplication")
    void testMultiplicationPriority() {
        Operation mul = new Multiplication();
        assertEquals(2, mul.getPriority());
    }
    
    @Test
    @DisplayName("Should return correct priority for power")
    void testPowerPriority() {
        Operation pow = new Power();
        assertEquals(3, pow.getPriority());
    }
    
    @Test
    @DisplayName("Should identify binary operations")
    void testBinaryOperationCount() {
        Operation add = new Addition();
        assertEquals(2, add.getOperandCount());
        assertFalse(add.isUnary());
    }
    
    @Test
    @DisplayName("Should identify unary operations")
    void testUnaryOperationCount() {
        Operation sqrt = new SquareRoot();
        assertEquals(1, sqrt.getOperandCount());
        assertTrue(sqrt.isUnary());
    }
    
    @Test
    @DisplayName("OperationFactory should create correct operations")
    void testOperationFactory() {
        assertEquals('+', OperationFactory.create('+').getSymbol());
        assertEquals('-', OperationFactory.create('-').getSymbol());
        assertEquals('*', OperationFactory.create('*').getSymbol());
        assertEquals('^', OperationFactory.create('^').getSymbol());
        assertEquals('√', OperationFactory.create('√').getSymbol());
    }
    
    @Test
    @DisplayName("OperationFactory should throw exception for unknown operator")
    void testOperationFactoryUnknownOperator() {
        assertThrows(IllegalArgumentException.class, () -> {
            OperationFactory.create('?');
        });
    }
}
