package com.icl.html_engine.enums;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum RegularExpressionPatterns {
    DIGIT_PATTERN("^[\\d]+([.]?[\\d]+)?"),
    UNARY_MINUS_PATTERN("^\\(\\-"),
    MATH_SYMBOLS_PATTERN("^(([(]+[\\-]?)*" +
            DIGIT_PATTERN.toString().substring(1) + "[)]*[" +
            getAllMathOperators() + "]?)+"),
    INCORRECT_BRACKETS_PATTERN(".*" + DIGIT_PATTERN.toString().substring(1) + "[(]");

    Pattern pattern;

    RegularExpressionPatterns(final String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Joins all math operators' String representation
     *
     * @return joined math operators in one String
     */
    public static String getAllMathOperators() {
        return MathOperator.getEnums().stream().map(MathOperator::getOperator).collect(Collectors.joining("\\"));
    }

    @Override
    public String toString() {
        return this.getPattern().toString();
    }
}
