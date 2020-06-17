package com.icl.html_engine.service.calculation;

import com.icl.html_engine.enums.MathOperator;
import com.icl.html_engine.exception.NotValidInputException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.NoSuchElementException;

import static com.icl.html_engine.enums.Messages.*;

@Component
public class Calculation implements ICalculation {
    /**
     * Calculates math expression from given String
     *
     * @param expression math expression in Reverse Polish notation
     * @return result of expression in BigDecimal representation
     */
    @Override
    public BigDecimal calculate(Deque<String> expression) throws ArithmeticException, NotValidInputException {
        Deque<BigDecimal> expressionResultStack = new ArrayDeque<>();

        String nextElementFromExpression;
        while ((nextElementFromExpression = expression.pollLast()) != null) {
            MathOperator operation;
            if ((operation = MathOperator.findByOperator(nextElementFromExpression)) != null) {
                calculateOperation(operation, expressionResultStack);
            } else {
                expressionResultStack.push(new BigDecimal(nextElementFromExpression));
            }
        }

        return expressionResultStack.pop();
    }

    /**
     * Method takes two numbers from calculationResultStack and applies {@code operator} to them
     *
     * @param operator               math binary operator
     * @param calculationResultStack stack which contains math expression
     * @throws NotValidInputException when there is no enough operands to calculate expression
     * @throws ArithmeticException    when division by zero occurs
     */
    public void calculateOperation(MathOperator operator, Deque<BigDecimal> calculationResultStack) throws NotValidInputException {
        BigDecimal firstOperand;
        BigDecimal secondOperand;
        try {
            ArrayList<? extends Number> numbers = new ArrayList<>();
            secondOperand = calculationResultStack.pop();
            firstOperand = calculationResultStack.pop();
        } catch (NoSuchElementException e) {
            throw new NotValidInputException(INCORRECT_EXPRESSION.toString(), e);
        }
        switch (operator) {
            case PLUS:
                calculationResultStack.push(firstOperand.add(secondOperand));
                break;
            case MINUS:
                calculationResultStack.push(firstOperand.subtract(secondOperand));
                break;
            case MULTIPLY:
                calculationResultStack.push(firstOperand.multiply(secondOperand));
                break;
            case DIVIDE:
                if (secondOperand.compareTo(BigDecimal.ZERO) != 0) {
                    calculationResultStack.push(firstOperand.divide(secondOperand, RoundingMode.HALF_UP));
                    break;
                } else {
                    throw new ArithmeticException(DIVISION_BY_ZERO.toString());
                }
            case POWER:
                calculationResultStack.push(BigDecimal.valueOf(Math.pow(firstOperand.doubleValue(), secondOperand.doubleValue())));
                break;
        }
    }
}
