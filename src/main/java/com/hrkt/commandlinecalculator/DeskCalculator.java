package com.hrkt.commandlinecalculator;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.var;

import java.math.BigDecimal;

/**
 * DeskCalculator
 *
 * see:
 * https://docs.oracle.com/javase/jp/1.3/api/java/math/BigDecimal.html
 */
public class DeskCalculator {
    private BigDecimal stack;
    private StringBuilder sb;

    enum Operator {
        PLUS {
            @Override
            BigDecimal apply(@NonNull BigDecimal lhs, @NonNull BigDecimal rhs) {
                return lhs.add(rhs);
            }
        },
        SUBTRACT{
            @Override
            BigDecimal apply(@NonNull BigDecimal lhs, @NonNull BigDecimal rhs) {
                return lhs.subtract(rhs);
            }
        }, MULTIPLY {
            @Override
            BigDecimal apply(@NonNull BigDecimal lhs, @NonNull BigDecimal rhs) {
                return lhs.multiply(rhs);
            }
        }, DIVIDE {
            @Override
            BigDecimal apply(@NonNull BigDecimal lhs, @NonNull BigDecimal rhs) {
                return lhs.divide(rhs, BigDecimal.ROUND_UNNECESSARY);//;
            }
        }, NONE {
            // allow null for rhs
            @Override
            BigDecimal apply(@NonNull BigDecimal lhs, BigDecimal rhs) {
                return lhs;
            }
        };

        abstract BigDecimal apply(BigDecimal lhs, BigDecimal rhs);
    }
    private Operator currentOperator = Operator.NONE;

    public DeskCalculator() {
        clearStack();
        clearBuffer();
    }

    public synchronized boolean pushChar(char c) {
        if(c == '.' && sb.toString().indexOf('.') < 0) {
            sb.append(c);
            return true;
        } else if('0' <=c && c <= '9') {
            sb.append(c);
            return true;
        }
        // do nothing.
        return false;
    }

    public synchronized BigDecimal getCurrentValue() {
        if(0 == sb.length()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(sb.toString());
    }

    public synchronized void pushPlusButton() {
        currentOperator = Operator.PLUS;
        stack = currentOperator.apply(stack, getCurrentValue());
        clearBuffer();
    }

    public synchronized BigDecimal pushEvalButton() {
        var v = new BigDecimal(sb.toString());

        if(Operator.NONE == currentOperator) {
            return v;
        }
        v = currentOperator.apply(stack, getCurrentValue());
        currentOperator = Operator.NONE;
        replaceBuffer(v.toPlainString());
        clearStack();
        return v;
    }

    public synchronized void pushClearButton() {
        currentOperator = Operator.NONE;
        clearBuffer();
        clearStack();
    }

    private synchronized void clearStack() {
        stack = BigDecimal.ZERO;
    }

    private synchronized void clearBuffer() {
        sb = new StringBuilder();
    }

    private synchronized void replaceBuffer(String s) {
        sb = new StringBuilder(s);
    }
}
