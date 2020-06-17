package com.icl.html_engine.service.scanner;

import com.icl.html_engine.exception.NotValidInputException;
import com.icl.html_engine.service.calculation.ICalculation;
import com.icl.html_engine.service.parser.IStringParser;
import com.icl.html_engine.service.validator.IValidator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@Data
public class MathExpressionScanner implements HtmlScanner<BigDecimal> {
    private IValidator mathExpressionsValidator;
    private IStringParser stringParser;
    private ICalculation calculation;

    @Autowired
    public MathExpressionScanner(IValidator mathExpressionsValidator, IStringParser stringParser, ICalculation calculation) {
        this.mathExpressionsValidator = mathExpressionsValidator;
        this.stringParser = stringParser;
        this.calculation = calculation;
    }

    @Override
    public BigDecimal scanHTML(String inputString, Map<String, Object> attributes) throws NotValidInputException {
        mathExpressionsValidator.validate(inputString);
        return calculation.calculate(stringParser.parse(inputString));
    }
}
