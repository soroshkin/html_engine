package com.icl.html_engine.service.validator;

import com.icl.html_engine.exception.NotValidInputException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VariableValidator implements IValidator {
    @Override
    public void validate(String inputString) throws NotValidInputException {
        Pattern pattern = Pattern.compile("(\\p{Alpha}+\\d*(\\.{1}\\p{Alpha}+\\d*(\\(\\))$)?)");
        Matcher matcher = pattern.matcher(inputString);

        if (!matcher.matches()) {
            throw new NotValidInputException(inputString);
        }
    }
}
