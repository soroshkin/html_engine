package com.icl.html_engine.service.validator;

import com.icl.html_engine.exception.NotValidInputException;

public interface IValidator {
    void validate(String inputString) throws NotValidInputException;
}
