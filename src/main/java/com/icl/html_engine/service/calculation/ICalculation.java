package com.icl.html_engine.service.calculation;

import com.icl.html_engine.exception.NotValidInputException;

import java.math.BigDecimal;
import java.util.Deque;

public interface ICalculation {
    BigDecimal calculate(Deque<String> expression) throws NotValidInputException;
}
