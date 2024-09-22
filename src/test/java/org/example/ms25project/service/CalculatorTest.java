package org.example.ms25project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CalculatorTest {

    @InjectMocks
    private Calculator calculator;

    @ParameterizedTest
    @CsvSource(
            {
                    "1, 2, 3",
                    "3, 4, 7",
                    "4, 5, 9"
            }
    )
    public void add(int a, int b, int expected) {
        int result = calculator.add(a, b);
        assertThat(result).isEqualTo(expected);
    }


    @Test
    public void givenTwoNumberDivideSuccess() {
        int result = calculator.divide(2, 2);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void givenTwoNumberDivideException() {
        assertThatThrownBy(
                () -> calculator.divide(2, 0)
        )
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Divide by zero");
    }

}