package io.github.eduardout.e_commerce.util;

import java.math.BigDecimal;

public class Calculation {
    public static final BigDecimal DEFAULT_AMOUNT = new BigDecimal("0.00");
    public static final Byte DEFAULT_PERCENTAGE = 0;

    private Calculation() {
        throw new UnsupportedOperationException("Utility class.");
    }
}
