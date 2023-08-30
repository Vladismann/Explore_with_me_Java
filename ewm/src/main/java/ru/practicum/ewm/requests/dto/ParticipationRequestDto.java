package ru.practicum.ewm.requests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.requests.model.StateParticipation;

import java.time.LocalDateTime;

import static ru.practicum.dto.Common.Constants.DEFAULT_DATE_FORMAT;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ParticipationRequestDto {

    @JsonFormat(pattern = DEFAULT_DATE_FORMAT)
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    private StateParticipation status;

}
