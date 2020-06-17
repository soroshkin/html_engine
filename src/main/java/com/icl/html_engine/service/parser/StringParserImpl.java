package com.icl.html_engine.service.parser;

import com.icl.html_engine.exception.IncorrectBracketsException;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Matcher;

import static com.icl.html_engine.enums.MathOperator.*;
import static com.icl.html_engine.enums.Messages.INCORRECT_BRACKETS;
import static com.icl.html_engine.enums.RegularExpressionPatterns.DIGIT_PATTERN;
import static com.icl.html_engine.enums.RegularExpressionPatterns.UNARY_MINUS_PATTERN;

@Service
public class StringParserImpl implements IStringParser {
    Deque<String> expressionToCalculate;
    Deque<String> mathOperationsStack;

    public StringParserImpl() {
        expressionToCalculate = new LinkedList<>();
        mathOperationsStack = new LinkedList<>();
    }

    /**
     * Parses given String to expression in Reverse Polish notation (RPN) form
     *
     * @param inputString - String to be parsed
     * @return Deque of strings, which represent expression in RPN form
     * @throws IncorrectBracketsException if brackets put incorrectly
     */
    @Override
    public Deque<String> parse(String inputString) throws IncorrectBracketsException {
        clearStacks();
        StringBuilder expressionString = new StringBuilder(inputString);

        while (expressionString.length() > 0) {
            if (isDigitOrUnaryMinusFound(expressionString)) {
                continue;
            }

            String currentSymbol = expressionString.substring(0, 1);
            expressionString.deleteCharAt(0);

            putOpenBracketInStackIfPresent(currentSymbol);
            manipulateWithExpressionInBrackets(currentSymbol);
            manipulateWithMathOperation(currentSymbol);
        }

        finishExpressionParsing();

        return expressionToCalculate;
    }

    /**
     * Clears  {@code expressionToCalculate} and {@code mathOperationsStack}
     * before each parsing
     */
    public void clearStacks() {
        expressionToCalculate.clear();
        mathOperationsStack.clear();
    }

    /**
     * Searches digits or unary minus operator and puts them into {@code expressionToCalculate}
     *
     * @param inputString - String to be parsed
     * @return {@code true} if digit or unary minus operator found, false if not
     */
    public boolean isDigitOrUnaryMinusFound(StringBuilder inputString) {
        Matcher matcher = DIGIT_PATTERN.getPattern().matcher(inputString);
        boolean isFound;
        if (isFound = matcher.find()) {
            expressionToCalculate.push(matcher.group());
            inputString.delete(0, matcher.end());
        }

        if (matcher.usePattern(UNARY_MINUS_PATTERN.getPattern()).find()) {
            inputString.delete(0, matcher.end());
            mathOperationsStack.push(OPEN_BRACKET);
            expressionToCalculate.push(String.valueOf(0));
            mathOperationsStack.push(MINUS.getOperator());
            isFound = true;
        }

        return isFound;
    }

    /**
     * Compares {@code currentSymbol} with {@code OPEN_BRACKET} and puts it into {@code mathOperationsStack}
     *
     * @param currentSymbol - symbol to be compared to {@code OPEN_BRACKET}
     */
    public void putOpenBracketInStackIfPresent(String currentSymbol) {
        if (currentSymbol.equals(OPEN_BRACKET)) {
            mathOperationsStack.push(currentSymbol);
        }
    }

    /**
     * Compares {@code currentSymbol} with {@code CLOSED_BRACKET} and puts all math operators from
     * {@code mathOperationsStack} into {@code expressionToCalculate} until it meets {@code OPEN_BRACKET}
     *
     * @param currentSymbol - symbol to be compared to {@code CLOSED_BRACKET}
     * @throws IncorrectBracketsException if {@code OPEN_BRACKET} is not found
     */
    public void manipulateWithExpressionInBrackets(String currentSymbol) throws IncorrectBracketsException {
        String lastOperatorInStack;
        try {
            while (currentSymbol.equals(CLOSED_BRACKET)
                    && !(lastOperatorInStack = mathOperationsStack.pop()).equals(OPEN_BRACKET)) {
                expressionToCalculate.push(lastOperatorInStack);
            }
        } catch (NoSuchElementException e) {
            throw new IncorrectBracketsException(INCORRECT_BRACKETS.toString());
        }
    }

    /**
     * If {@code currentSymbol} is math operator, then method pushes all math operators to {@code expressionToCalculate}
     * from {@code mathOperationsStack}, whose priority equals or higher than {@code currentSymbol}
     *
     * @param currentSymbol - symbol to be compared to {@code MathOperator}
     */
    public void manipulateWithMathOperation(String currentSymbol) {
        if (isMathOperation(currentSymbol)) {
            String lastOperatorInStack;
            while ((lastOperatorInStack = mathOperationsStack.peek()) != null
                    && isMathOperation(lastOperatorInStack)
                    && hasHigherOrEqualPriority(lastOperatorInStack, currentSymbol)) {
                expressionToCalculate.push(mathOperationsStack.pop());
            }
            mathOperationsStack.push(currentSymbol);
        }
    }

    /**
     * Takes all elements from {@code mathOperationsStack} and pushes them into
     * {@code expressionToCalculate}
     *
     * @throws IncorrectBracketsException thrown if {@code mathOperationsStack} contains {@code OPEN_BRACKET}
     */
    public void finishExpressionParsing() throws IncorrectBracketsException {
        if (!mathOperationsStack.contains(OPEN_BRACKET)) {
            mathOperationsStack.forEach(expressionToCalculate::push);
        } else {
            throw new IncorrectBracketsException(INCORRECT_BRACKETS.toString());
        }
    }

    /**
     * Checks whether String is arithmetical operation
     *
     * @param textToCheck the text which has to be checked whether it is math operator or not
     * @return {@code true} String is arithmetical operation
     */
    @Override
    public boolean isMathOperation(String textToCheck) {
        return contains(textToCheck);
    }

    /**
     * @param firstOperation  arithmetical operation represented by String
     * @param secondOperation arithmetical operation represented by String
     * @return {@code true} if first argument has higher priority
     */
    @Override
    public boolean hasHigherOrEqualPriority(String firstOperation, String secondOperation) {
        return Objects.requireNonNull(findByOperator(firstOperation)).getPriority()
                .compareTo(Objects.requireNonNull(findByOperator(secondOperation)).getPriority()) >= 0;
    }
}
