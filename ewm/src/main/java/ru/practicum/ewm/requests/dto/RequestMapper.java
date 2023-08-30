package ru.practicum.ewm.requests.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.requests.model.Request;

@UtilityClass
public class RequestMapper {

    public ParticipationRequestDto requestToParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequestor().getId())
                .status(request.getStatus())
                .build();
    }
}
