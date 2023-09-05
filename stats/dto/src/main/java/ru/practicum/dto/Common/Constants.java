package ru.practicum.dto.Common;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class Constants {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
}
