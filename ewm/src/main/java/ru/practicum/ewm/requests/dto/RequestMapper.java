package ru.practicum.ewm.requests.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.requests.model.Request;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RequestMapper {

    public ParticipationRequestDto requestToParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> requestToParticipationRequestDto(List<Request> requests) {
        return requests.stream().map(RequestMapper::requestToParticipationRequestDto).collect(Collectors.toList());
    }
}
