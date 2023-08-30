package ru.practicum.ewm.event.service.adminEvent;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.dto.search.AdminSearchParameters;

import java.util.List;

public interface AdminEventService {

    EventFullDto updateEvent(Long eventId, UpdateEventUserRequest request);

    List<EventFullDto> getAll(AdminSearchParameters request);
}
