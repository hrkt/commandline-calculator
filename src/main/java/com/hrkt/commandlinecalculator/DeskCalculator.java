package com.hrkt.commandlinecalculator;

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

    private enum Operator {
        PLUS, SUBTRACT, MULTIPLY, DIVIDE, NONE
    }
    private Operator currentOperator = Operator.NONE;

    public DeskCalculator() {
        clearStack();
        clearBuffer();
    }

    public synchronized void pushChar(char c) {
        if(c == '.' && sb.toString().indexOf('.') < 0) {
            sb.append(c);
        } else if('0' <=c && c <= '9') {
            sb.append(c);
        }
        // do nothing.
    }

    public synchronized BigDecimal getCurrentValue() {
        if(0 == sb.length()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(sb.toString());
    }

    public synchronized void pushPlusButton() {
        stack = stack.add(getCurrentValue());
        currentOperator = Operator.PLUS;
        clearBuffer();
    }

    public synchronized BigDecimal pushEvalButton() {
        var v = new BigDecimal(sb.toString());
        switch(currentOperator) {
            case PLUS: {
                v = stack.add(getCurrentValue());
                break;
            }
            case SUBTRACT: {
//                v = stack.subtract(getCurrentValue());
//                break;
            }
            case MULTIPLY: {
//                v = stack.multiply(getCurrentValue());
//                break;
            }
            case DIVIDE: {
//                v = stack.divide(getCurrentValue(), BigDecimal.ROUND_UNNECESSARY);//
//                break;
                throw new RuntimeException("Not implemented yet.");
            }
            case NONE: {
                return v;
            }
            default: {
                throw new RuntimeException("Not defined.");
            }
        }
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
