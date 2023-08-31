package ru.practicum.ewm.event.service.privateEvent;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    List<EventFullDto> getUserEvents(long userId, int from, int size);

    EventFullDto getUserEvent(long userId, long eventId);

    EventFullDto updateEvent(long userId, long eventId, UpdateEventRequest newEvent);

    List<ParticipationRequestDto> getAllRequestsByEvent(Long eventId, Long userId);

    List<ParticipationRequestDto> updateEventRequestsStatus(Long eventId, Long userId, EventRequestStatusUpdateRequest requestsForUpdate);
}
