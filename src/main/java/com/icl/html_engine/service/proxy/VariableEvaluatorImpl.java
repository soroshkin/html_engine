package com.icl.html_engine.service.proxy;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class VariableEvaluatorImpl implements IVariableEvaluator {
    @Override
    public String evaluate(List<String> variableInvocationList, Object attribute) throws InvocationTargetException, IllegalAccessException {
        Object result = String.join("\\.", variableInvocationList);

        if (variableInvocationList.size() <= 1) {
            return attribute.toString();
        }

        if (attribute != null) {
            for (Method method : attribute.getClass().getMethods()) {
                if ((method.getName() + "()").equals(variableInvocationList.get(1))) {
                    result = method.invoke(attribute);
                }
            }
        }

        return result.toString();
    }
}
