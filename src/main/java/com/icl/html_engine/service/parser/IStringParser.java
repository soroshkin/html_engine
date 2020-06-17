package com.icl.html_engine.service.parser;

import com.icl.html_engine.exception.NotValidInputException;

import java.util.Deque;

public interface IStringParser {
    String OPEN_BRACKET = "(";
    String CLOSED_BRACKET = ")";

    Deque<String> parse(String inputString) throws NotValidInputException;

    boolean isMathOperation(String textToCheck);

    boolean hasHigherOrEqualPriority(String firstOperation, String secondOperation);
}
