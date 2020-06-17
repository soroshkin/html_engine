package com.icl.html_engine.service;

import com.icl.html_engine.exception.NotValidInputException;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface IEngine {
    Map<String, Object> getAttributes();

    String parseHTML(String inputString) throws NotValidInputException, InvocationTargetException, IllegalAccessException;

    void addAttribute(String name, Object attribute);
}
