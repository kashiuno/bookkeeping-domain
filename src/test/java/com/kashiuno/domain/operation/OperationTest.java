package com.kashiuno.domain.operation;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationTest {

    @Test
    void of_whenAmountValidNumber_shouldReturnOperationWithAmountEqualsParameter() {
        BigDecimal actual = BigDecimal.TEN;

        assertEquals(actual.setScale(2, RoundingMode.HALF_UP), Operation.of(actual).getAmount());
    }

    @Test
    void of_whenAmountIsNull_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Operation.of(null));
    }
}