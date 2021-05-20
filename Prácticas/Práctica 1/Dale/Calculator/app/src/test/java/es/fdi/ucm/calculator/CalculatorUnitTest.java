package es.fdi.ucm.calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculatorUnitTest {
    private Calculator calculator = new Calculator();

    @Test
    public void addition_isCorrect() {
        int sum = calculator.add(3,3);
        assertEquals(6, sum);
    }
}
