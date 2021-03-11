package es.ucm.fdi.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorUnitTest {
    Calculator calc = new Calculator();

    @Test
    public void additionCorrect(){
        Integer expected = 3;
        Integer result = calc.add(1,2);
        assertEquals(expected,result);
    }
}
