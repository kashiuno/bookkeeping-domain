package com.kashiuno.backend.domain.operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

/**
 * Immutable. Class which allows us to operate with Account
 */
public final class Operation {
    private final BigDecimal amount;

    private Operation(BigDecimal amount) {
        this.amount = amount;
    }

    public static Operation of(BigDecimal amount) {
        BigDecimal nonNullAmount = Optional.ofNullable(amount).orElseThrow(() -> new IllegalArgumentException("Amount cannot be null"));
        return new Operation(nonNullAmount.setScale(2, RoundingMode.HALF_UP));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return amount.equals(operation.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
