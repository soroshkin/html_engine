package com.icl.html_engine.service.scanner;

import com.icl.html_engine.exception.NotValidInputException;
import com.icl.html_engine.service.proxy.IVariableEvaluator;
import com.icl.html_engine.service.validator.IValidator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Data
public class VariableScanner implements HtmlScanner<String> {
    private IValidator variableValidator;
    private IVariableEvaluator variableEvaluator;

    @Autowired
    public VariableScanner(IValidator variableValidator, IVariableEvaluator variableEvaluator) {
        this.variableValidator = variableValidator;
        this.variableEvaluator = variableEvaluator;
    }

    @Override
    public String scanHTML(String inputString, Map<String, Object> attributes)
            throws NotValidInputException, InvocationTargetException, IllegalAccessException {
        variableValidator.validate(inputString);
        List<String> parsedStringList = Arrays.asList(inputString.split("\\."));

        return variableEvaluator.evaluate(parsedStringList, attributes.get(parsedStringList.get(0)));
    }
}
