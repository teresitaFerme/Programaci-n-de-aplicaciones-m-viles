package es.ucm.fdi.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorUnitTest {
    @Test
    public void addition_isCorrect() {
        Calculator calculator = new Calculator();
        int result = calculator.add(1, 2);
        assertEquals(3, result);
    }
}
