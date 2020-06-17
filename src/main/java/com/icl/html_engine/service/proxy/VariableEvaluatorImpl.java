package com.icl.html_engine.service.proxy;

import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class VariableEvaluatorImpl implements IVariableEvaluator {
    @Override
    public String evaluate(List<String> variableInvocationList, Object attribute)
            throws InvocationTargetException, IllegalAccessException {
        String convertedString = attribute.toString();

        if (variableInvocationList.size() <= 1) {
            return convertedString;
        }

        if (attribute != null) {
            for (Method method : attribute.getClass().getMethods()) {
                if ((method.getName() + "()").equals(variableInvocationList.get(1))) {
                    convertedString = method.invoke(attribute).toString();
                }
            }
        } else {
            convertedString = String.join("\\.", variableInvocationList);
        }

        return convertedString;
    }
}
