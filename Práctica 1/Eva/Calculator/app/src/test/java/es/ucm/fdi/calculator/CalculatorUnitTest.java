package es.ucm.fdi.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorUnitTest {

    @Test
    public void testCalculator(){
        Calculator cal = new Calculator();
        long sum = cal.add(1,2);
        assertEquals(3, sum);
    }

}
