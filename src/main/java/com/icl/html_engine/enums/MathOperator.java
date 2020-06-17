package com.icl.html_engine.enums;

import java.util.EnumSet;

public enum MathOperator {
    PLUS("+", 1),
    MINUS("-", 1),
    MULTIPLY("*", 2),
    DIVIDE("/", 2),
    POWER("^", 3);

    private String operator;
    private Integer priority;

    private static EnumSet<MathOperator> enums = EnumSet.allOf(MathOperator.class);

    MathOperator(String operator, Integer priority) {
        this.operator = operator;
        this.priority = priority;
    }

    public String getOperator() {
        return operator;
    }

    public Integer getPriority() {
        return priority;
    }

    public static EnumSet<MathOperator> getEnums() {
        return enums;
    }

    /**
     * Searches math operator by given String representation
     * @param operator is String representation of math operation
     * @return MathOperator or null
     */
    public static MathOperator findByOperator(String operator) {
       return enums.stream().filter((element)->
               element.getOperator().equalsIgnoreCase(operator)).findFirst().orElse(null);
    }

    public static boolean contains(String operator){
        return getEnums().contains(findByOperator(operator));
    }

}
