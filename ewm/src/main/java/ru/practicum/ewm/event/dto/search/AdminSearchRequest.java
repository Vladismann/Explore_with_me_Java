package ru.practicum.ewm.event.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewm.event.model.EventState;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.dto.Common.Constants.DEFAULT_DATE_FORMAT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminSearchRequest {

    private List<Long> users;
    private List<EventState> states;
    private List<Long> categories;
    @DateTimeFormat(pattern = DEFAULT_DATE_FORMAT)
    private LocalDateTime rangeStart;
    @DateTimeFormat(pattern = DEFAULT_DATE_FORMAT)
    private LocalDateTime rangeEnd;
    @PositiveOrZero
    private int from = 0;
    @Positive
    private int size = 10;
}
