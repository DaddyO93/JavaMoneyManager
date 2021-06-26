package com.moneyManager;

import org.junit.Test;
import com.moneyManager.DoMath;

import static org.junit.Assert.assertEquals;

public class DoMathTest {
    private final DoMath MathTest = new DoMath();

    @Test
    public void addNumbers() {
        int result = MathTest.addNumbers(1, 1);
        int correct = 2;

        assertEquals(correct,result);
    }

    @Test
    public void subtractNumbers(){
        int result = MathTest.subtractNumbers(1,1);
        int correct = 0;

        assertEquals(correct, result);
    }

    @Test
    public void divideNumbers(){
        int result = MathTest.divideNumbers(1,1);
        int correct = 1;

        assertEquals(correct,result);
    }

    @Test
    public void multiplyNumbers(){
        int result = MathTest.multiplyNumbers(1,1);
        int correct = 1;

        assertEquals(correct, result);
    }
}