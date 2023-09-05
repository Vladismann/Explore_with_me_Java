package ru.practicum.ewm.event.service.adminEvent;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.event.dto.search.AdminSearchRequest;

import java.util.List;

public interface AdminEventService {

    EventFullDto updateEvent(Long eventId, UpdateEventRequest request);

    List<EventFullDto> getAll(AdminSearchRequest request);
}
