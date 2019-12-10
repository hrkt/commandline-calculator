package com.hrkt.commandlinecalculator;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * please write expected value in "new BigDecimal(string)" when you write testcases for floating point argument.
 *
 * see: https://docs.oracle.com/javase/jp/1.3/api/java/math/BigDecimal.html
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeskCalculatorTest {
    @Test
    public void init_success() {
        DeskCalculator dc = new DeskCalculator();
        assertThat(dc.getCurrentValue()).isEqualTo(BigDecimal.ZERO);
    }

    @ParameterizedTest
    @ValueSource(chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'})
    public void push_single_success(char c) {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar(c);
        assertThat(dc.getCurrentValue()).isEqualTo(new BigDecimal(new String(new char[]{c})));
    }

    @ParameterizedTest
    @ValueSource(chars = {'+', '-', '*', '/', 'A', '!', ' '})
    public void push_single_invalid_char_success(char c) {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar(c);
        assertThat(dc.getCurrentValue()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void push_1_2_success() {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar('1');
        assertThat(dc.getCurrentValue()).isEqualTo(BigDecimal.valueOf(1));
        dc.pushChar('2');
        assertThat(dc.getCurrentValue()).isEqualTo(BigDecimal.valueOf(12));
    }

    @Test
    public void push_with_last_char_period_success() {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar('1');
        assertThat(dc.getCurrentValue()).isEqualTo(BigDecimal.valueOf(1));
        dc.pushChar('.');
        assertThat(dc.getCurrentValue()).isEqualTo(BigDecimal.valueOf(1));
    }

    @Test
    public void push_1_period_0_success() {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar('1');
        dc.pushChar('.');
        dc.pushChar('0');
        assertThat(dc.getCurrentValue()).isEqualTo(new BigDecimal("1.0"));
    }

    @Test
    public void push_1_period_2_period_3_uccess() {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar('1');
        dc.pushChar('.');
        dc.pushChar('2');
        dc.pushChar('.');
        dc.pushChar('3');
        assertThat(dc.getCurrentValue()).isEqualTo(new BigDecimal("1.23"));
    }

    @Test
    public void push_period_2_period_3_uccess() {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar('.');
        dc.pushChar('2');
        dc.pushChar('3');
        assertThat(dc.getCurrentValue()).isEqualTo(new BigDecimal("0.23"));
    }

    @Test
    public void calc_1_plus_2_success() {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar('1');
        dc.pushPlusButton();
        dc.pushChar('2');
        val actual = dc.pushEvalButton();
        assertThat(actual).isEqualTo(new BigDecimal(3));
    }

    @Test
    public void calc_1_plus_2_plu2_3_success() {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar('1');
        dc.pushPlusButton();
        dc.pushChar('2');
        dc.pushPlusButton();
        dc.pushChar('3');
        val actual = dc.pushEvalButton();
        assertThat(actual).isEqualTo(new BigDecimal(6));
    }

    @Test
    public void push_eval_button_twice_not_change_value_success() {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar('1');
        dc.pushPlusButton();
        dc.pushChar('2');
        val actual1 = dc.pushEvalButton();
        assertThat(actual1).isEqualTo(new BigDecimal(3));
        val actual2 = dc.pushEvalButton();
        assertThat(actual2).isEqualTo(new BigDecimal(3));
    }

    @Test
    public void calc_1_plus_2_eval_plu2_3_success() {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar('1');
        dc.pushPlusButton();
        dc.pushChar('2');
        dc.pushEvalButton();
        dc.pushPlusButton();
        dc.pushChar('3');
        val actual = dc.pushEvalButton();
        assertThat(actual).isEqualTo(new BigDecimal(6));
    }

    @Test
    public void clear_success() {
        DeskCalculator dc = new DeskCalculator();
        dc.pushChar('1');
        dc.pushClearButton();
        val actual = dc.getCurrentValue();
        assertThat(actual).isEqualTo(BigDecimal.ZERO);
    }
}