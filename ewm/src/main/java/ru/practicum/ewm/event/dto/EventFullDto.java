package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.dto.Common.Constants.DEFAULT_DATE_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventFullDto {

    private String annotation;

    private CategoryDto category;

    private long confirmedRequests;

    @JsonFormat(pattern = DEFAULT_DATE_FORMAT)
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(pattern = DEFAULT_DATE_FORMAT)
    private LocalDateTime eventDate;

    private Long id;

    private UserShortDto initiator;

    private LocationDto location;

    private boolean paid;

    private int participantLimit;

    @JsonFormat(pattern = DEFAULT_DATE_FORMAT)
    private LocalDateTime publishedOn;

    private boolean requestModeration;

    private EventState state;

    private String title;

    private long views;
}