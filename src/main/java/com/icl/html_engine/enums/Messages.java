package com.icl.html_engine.enums;

public enum Messages {
    RESULT_OF_EXPRESSION("Результат выражения: "),
    INCORRECT_BRACKETS("Несогласованные скобки в выражении."),
    ILLEGAL_BRACKETS("Неправильная расстановка скобок в выражении."),
    INCORRECT_EXPRESSION("Некорректное выражение."),
    DIVISION_BY_ZERO("Нельзя делить на ноль."),
    EMPTY_STRING("Введена пустая строка."),
    ILLEGAL_CHARACTER("^ недопустимый символ под индексом "),
    INPUT_EXPRESSION("Введите математическое выражение." +
            "\nПример: (-3) + 5 * ((-8) - 1)^2." +
            "\nДля выхода введите ");

    String message;

    Messages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
