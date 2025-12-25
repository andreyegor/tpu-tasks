package lab.calc.api;

import lab.calc.impl.DefaultCalculator;
import lab.calc.impl.DefaultExpressionParser;

public final class CalculatorFactory {
    private CalculatorFactory() {

    }
    
     // я хотел и затенить существование отдельной реализации, чтобы пакет выглядел целостно
     // и оставить возможность тестирования, отделив реализацию от контракта
     // мне не нравится как я решил эту задачу
    public static Calculator createCalculator() {
        return new DefaultCalculator();
    }
    
    public static ExpressionParser createExpressionParser() {
        return new DefaultExpressionParser();
    }
}
