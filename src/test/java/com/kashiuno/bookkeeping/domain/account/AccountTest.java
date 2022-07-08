package com.kashiuno.bookkeeping.domain.account;

import com.kashiuno.bookkeeping.domain.operation.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void ofZero_whenNothing_shouldCreateNewAccountWithCreditEqualsZeroAndScaleEquals2() {
        Account zeroAccount = Account.ofZero();

        assertEquals(zeroAccount, Account.of(BigDecimal.ZERO));
        assertEquals(2, zeroAccount.getCredit().scale());
    }

    @Test
    void of_whenValidCredit_shouldReturnNewAccountWithCreditEqualsParameterAndScaleEquals2() {
        Account account = Account.of(BigDecimal.TEN);

        assertEquals(account, Account.of(BigDecimal.TEN));
        assertEquals(2, account.getCredit().scale());
    }

    @Test
    void of_whenCreditIsNull_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Account.of(null));
    }

    @Test
    void of_whenCreditIsLessThenZero_shouldReturnNewAccountWithCreditEqualsParameter() {
        BigDecimal creditLessThenZero = BigDecimal.valueOf(-5.667);

        assertEquals(creditLessThenZero.setScale(2, RoundingMode.HALF_UP), Account.of(creditLessThenZero).getCredit());
    }

    @ParameterizedTest
    @MethodSource("provideParametersForApplyOperation")
    void applyOperation(BigDecimal expectedValue, Account account, Operation operation) {
        assertEquals(expectedValue, account.applyOperation(operation).getCredit());
    }

    private static Stream<Arguments> provideParametersForApplyOperation() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), Account.of(BigDecimal.TEN), Operation.of(BigDecimal.valueOf(90))),
                Arguments.of(BigDecimal.valueOf(100.55), Account.of(BigDecimal.valueOf(100)), Operation.of(BigDecimal.valueOf(0.554))),
                Arguments.of(BigDecimal.valueOf(100.56), Account.of(BigDecimal.valueOf(100)), Operation.of(BigDecimal.valueOf(0.556))),
                Arguments.of(BigDecimal.valueOf(96).setScale(2, RoundingMode.HALF_UP), Account.of(BigDecimal.valueOf(50.9543)), Operation.of(BigDecimal.valueOf(45.049))),
                Arguments.of(BigDecimal.valueOf(6.95), Account.of(BigDecimal.TEN), Operation.of(BigDecimal.valueOf(-3.05434))),
                Arguments.of(BigDecimal.valueOf(6.95), Account.of(BigDecimal.TEN), Operation.of(BigDecimal.valueOf(-3.04738))),
                Arguments.of(BigDecimal.valueOf(-100.55), Account.of(BigDecimal.valueOf(100)), Operation.of(BigDecimal.valueOf(-200.55))),
                Arguments.of(BigDecimal.valueOf(-100.55), Account.of(BigDecimal.valueOf(-100)), Operation.of(BigDecimal.valueOf(-0.55))),
                Arguments.of(BigDecimal.valueOf(-103.54), Account.of(BigDecimal.valueOf(-50)), Operation.of(BigDecimal.valueOf(-53.539)))
        );
    }
}