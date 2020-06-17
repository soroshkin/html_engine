package com.icl.html_engine.service.proxy;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IVariableEvaluator {
    String evaluate(List<String> variableInvocationList, Object attribute) throws InvocationTargetException, IllegalAccessException;
}
