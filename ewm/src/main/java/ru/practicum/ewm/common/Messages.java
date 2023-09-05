package ru.practicum.ewm.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {

    //Paging
    public static final String INCORRECT_OFFSET = "Начальный элемент страницы не может быть меньше нуля";
    public static final String INCORRECT_LIMIT = "Лимит страницы не может быть меньше или равен нулю";
}
