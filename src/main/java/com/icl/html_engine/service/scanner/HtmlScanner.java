package com.icl.html_engine.service.scanner;

import com.icl.html_engine.exception.NotValidInputException;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface HtmlScanner<T> {
    T scanHTML(String inputString, Map<String, Object> attributes)
            throws NotValidInputException, InvocationTargetException, IllegalAccessException;
}
