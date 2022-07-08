package com.kashiuno.bookkeeping.domain.account;

import com.kashiuno.bookkeeping.domain.operation.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

/**
 * Immutable. Account class apply role of main storage for money. It can only work with Operation class {@link Operation}.
 */
public final class Account {
    private final BigDecimal credit;

    private Account(BigDecimal credit) {
        this.credit = credit;
    }

    public static Account ofZero() {
        return new Account(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
    }

    public static Account of(BigDecimal credit) {
        BigDecimal nonNullCredit = Optional.ofNullable(credit).orElseThrow(() -> new IllegalArgumentException("Credit cannot be null"));
        return new Account(nonNullCredit.setScale(2, RoundingMode.HALF_UP));
    }

    public Account applyOperation(Operation operation) {
        return Account.of(this.credit.add(operation.getAmount()));
    }

    public BigDecimal getCredit() {
        return credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return credit.equals(account.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credit);
    }
}
