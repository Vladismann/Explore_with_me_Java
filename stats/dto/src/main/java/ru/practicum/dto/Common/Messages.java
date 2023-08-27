package ru.practicum.dto.Common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {

    //Methods
    public static final String RECEIVED_GET = "Получен запрос GET {}/{}";
    public static final String RECEIVED_POST = "Получен запрос POST {}/{}";
    public static final String RECEIVED_PATCH = "Получен запрос PATCH {}/{}";
    public static final String RECEIVED_DELETE = "Получен запрос DELETE {}/{}";

    //Error reasons
    public static final String INCORRECT_DATA = "Incorrectly made request.";
    public static final String NOT_FOUND = "The required object was not found.";
    public static final String INTEGRITY_CONSTRAINT= "Integrity constraint has been violated.";

}
