package ru.practicum.ewm.requests.service;

import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestDto updateRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getAll(Long userId);
}
