package com.moneyManager;

import org.junit.Assert;
import org.junit.Test;

public class ConvertToCurrencyTest {

    @Test
    public void convertToCurrencyGoodEntry() {
        String result = DataScrubber.convertToCurrency("1");
        String correct = "$1.00";
        Assert.assertEquals(correct, result);
    }

    @Test
    public void convertToCurrencyIncludingNonAlphaNumericEntry() {
        String result = DataScrubber.convertToCurrency("1-a");
        String correct = "$0";
        Assert.assertEquals(correct, result);
    }

    @Test
    public void convertToCurrencyOnlyNonNumbers() {
        String result = DataScrubber.convertToCurrency("ab-+/");
        String correct = "$0";
        Assert.assertEquals(correct, result);
    }
}