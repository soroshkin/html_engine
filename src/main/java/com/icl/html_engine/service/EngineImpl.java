package com.icl.html_engine.service;

import com.icl.html_engine.exception.NotValidInputException;
import com.icl.html_engine.service.scanner.HtmlScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EngineImpl implements IEngine {
    private HtmlScanner<BigDecimal> mathExpressionsScanner;
    private HtmlScanner<String> variableScanner;

    @Autowired
    public EngineImpl(@Qualifier("mathExpressionScanner") HtmlScanner<BigDecimal> mathExpressionsScanner,
                      @Qualifier("variableScanner") HtmlScanner<String> variableScanner) {
        this.mathExpressionsScanner = mathExpressionsScanner;
        this.variableScanner = variableScanner;
    }

    private Map<String, Object> attributes = new ConcurrentHashMap<>();

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Scans HTML page for {} tags and parses them into math equation, standalone variable
     * or variable method.
     * @param inputString String representation of HTML page to be scanned
     * @return parsed HTML page as String
     * @throws NotValidInputException if variable or math equation contain illegal characters or wrong.
     */
    @Override
    public String parseHTML(String inputString)
            throws NotValidInputException, InvocationTargetException, IllegalAccessException {
        Pattern pattern = Pattern.compile("\\{[^{^}]+}");
        Matcher matcher = pattern.matcher(inputString);
        StringBuffer convertedString = new StringBuffer();

        while (matcher.find()) {
            String stringInTags = matcher.group().replaceAll("\\{|}|\\s+", "");
            Matcher variableMatcher = Pattern.compile("\\p{Alpha}").matcher(stringInTags);

            if (variableMatcher.find()) {
                matcher.appendReplacement(convertedString,
                        variableScanner.scanHTML(stringInTags, attributes));
            } else {
                matcher.appendReplacement(convertedString,
                        mathExpressionsScanner.scanHTML(stringInTags, attributes).toPlainString());
            }
        }
        matcher.appendTail(convertedString);

        return convertedString.toString();
    }

    /**
     * Adds attribute to context.
     * @param name attribute name.
     * @param attribute object.
     */
    @Override
    public void addAttribute(String name, Object attribute) {
        this.attributes.put(name, attribute);
    }
}
