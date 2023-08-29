package ru.practicum.ewm.event.service.privateEvent;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;

import java.util.List;

public interface PrivateEventService {

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    List<EventFullDto> getUserEvents(long userId, int from, int size);

    EventFullDto getUserEvent(long userId, long eventId);
}
