package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.ewm.event.model.Location;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.dto.Common.Constants.DEFAULT_DATE_FORMAT;

public class NewEventDto {

    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @NotNull
    private Long category;

    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

    @Future
    @JsonFormat(pattern = DEFAULT_DATE_FORMAT)
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    private int participantLimit;

    private boolean paid;

    private boolean requestModeration;

    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
