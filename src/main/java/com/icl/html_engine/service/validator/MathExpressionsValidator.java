package com.icl.html_engine.service.validator;

import com.icl.html_engine.exception.EmptyExpressionException;
import com.icl.html_engine.exception.IllegalCharacterException;
import com.icl.html_engine.exception.IncorrectBracketsException;
import com.icl.html_engine.exception.NotValidInputException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

import static com.icl.html_engine.enums.Messages.*;
import static com.icl.html_engine.enums.RegularExpressionPatterns.INCORRECT_BRACKETS_PATTERN;
import static com.icl.html_engine.enums.RegularExpressionPatterns.MATH_SYMBOLS_PATTERN;

@Component
public class MathExpressionsValidator implements IValidator {
    @Override
    public void validate(String inputString) throws NotValidInputException {
        if (inputString == null || inputString.isEmpty()) {
            throw new EmptyExpressionException(EMPTY_STRING.toString());
        }
        if (INCORRECT_BRACKETS_PATTERN.getPattern().matcher(inputString).find()) {
            throw new IncorrectBracketsException(ILLEGAL_BRACKETS.toString());
        }
        checkExpressionForIllegalCharacters(inputString);
    }

    /**
     * Method tries to check {@code inputString} for illegal characters,
     * double operators or invalid operators' sequence.
     * If {@code inputString} doesn't match regular expression, then exception will contain
     * message, which points to illegal character
     *
     * @param inputString math expression to be calculated without whitespaces between symbols
     * @throws IllegalCharacterException if given {@code inputString} is not valid mathematical expression
     */
    public void checkExpressionForIllegalCharacters(String inputString) throws IllegalCharacterException {
        Matcher matcher = MATH_SYMBOLS_PATTERN.getPattern().matcher(inputString);
        StringBuilder stringBuilder = new StringBuilder();

        int index = 0;
        if (matcher.find()) {
            stringBuilder.append(matcher.group());
            index = matcher.end();
        }

        if (index != inputString.length()) {
            stringBuilder.insert(0, "\n" + " ");
            stringBuilder.append(String.format(inputString.substring(index) + "\n"
                    + "%" + (++index) + "s%s%d \n", " ", ILLEGAL_CHARACTER, index));
            throw new IllegalCharacterException(stringBuilder.toString());
        }
    }
}
